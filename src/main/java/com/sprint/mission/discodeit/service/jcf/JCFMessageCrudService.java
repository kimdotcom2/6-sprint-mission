package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.JCFChannelService;
import com.sprint.mission.discodeit.service.JCFMessageService;
import com.sprint.mission.discodeit.service.JCFUserService;

import java.util.*;

public class JCFMessageCrudService implements JCFMessageService {

    private final Map<UUID, Message> data;
    private final JCFUserService userService;
    private final JCFChannelService channelService;

    public JCFMessageCrudService(JCFUserService userService, JCFChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
        data = new HashMap<>();
    }

    @Override
    public void create(Message message) {

        if (!userService.existById(message.getUserId())) {

            System.out.println("No such user.");
            return;

        }

        if (!channelService.existById(message.getChannelId())) {

            System.out.println("No such channel.");
            return;

        }

        data.put(message.getId(), message);

    }

    @Override
    public boolean existById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public Optional<Message> readById(UUID id) {

        if (!data.containsKey(id)) {
            return Optional.empty();
        }

        Message message = data.get(id);
        //System.out.println(message.toString());
        return Optional.ofNullable(message);

    }

    @Override
    public List<Message> readChildrenById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        List<Message> childMessageList = data.values().stream()
                .filter(message -> message.isReply() && message.getParentMessageId().equals(id))
                .toList();

        System.out.println("답글 수 : " + childMessageList.size());

        /*childMessageList.stream().forEach(message -> {
            System.out.println(message.toString());
        });*/

        return childMessageList;

    }

    @Override
    public List<Message> readAll() {

        System.out.println("Message List:");

        /*data.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getValue().getContent());
        });*/
        return List.of(data.values().toArray(new Message[0]));

    }

    @Override
    public void update(UUID id, String content, boolean isReply, UUID parentMessageId) {

        if (!data.containsKey(id)) {

            System.out.println("No such message.");
            return;

        }

        data.get(id).update(content, isReply, parentMessageId);

    }

    @Override
    public void deleteById(UUID id) {

        if (!data.containsKey(id)) {

            System.out.println("No such message.");
            return;

        }

        data.remove(id);

    }
}
