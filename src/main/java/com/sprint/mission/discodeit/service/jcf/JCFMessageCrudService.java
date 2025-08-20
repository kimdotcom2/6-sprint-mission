package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFMessageCrudService implements MessageService {

    private final Map<UUID, Message> data;
    private final UserService userService;
    private final ChannelService channelService;

    public JCFMessageCrudService(UserService userService, ChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
        data = new TreeMap<>();
    }

    @Override
    public void create(Message message) {

        if (!userService.existById(message.getUserId())) {
            throw new IllegalArgumentException("No such user.");
        }

        if (!channelService.existById(message.getChannelId())) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (data.containsKey(message.getId())) {
            throw new IllegalArgumentException("Message already exists.");
        }

        data.put(message.getId(), message);

    }

    @Override
    public boolean existById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public Optional<Message> findMessageById(UUID id) {

        if (!data.containsKey(id)) {
            return Optional.empty();
        }

        Message message = data.get(id);
        //System.out.println(message.toString());
        return Optional.ofNullable(message);

    }

    @Override
    public List<Message> findChildMessagesById(UUID id) {

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
    public List<Message> findAllMessages() {

        System.out.println("Message List:");

        /*data.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getValue().getContent());
        });*/
        return List.of(data.values().toArray(new Message[0]));

    }

    @Override
    public void update(UUID id, String content, boolean isReply, UUID parentMessageId) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        if (data.containsKey(parentMessageId) && !isReply) {
            throw new IllegalArgumentException();
        }

        data.get(id).update(content, isReply, parentMessageId);

    }

    @Override
    public void deleteById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        data.remove(id);

    }
}
