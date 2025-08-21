package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFMessageService implements MessageService {

    private final Map<UUID, Message> data;
    private final UserService userService;
    private final ChannelService channelService;

    public JCFMessageService(UserService userService, ChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
        data = new TreeMap<>();
    }

    @Override
    public void createMessage(Message message) {

        if (!userService.existUserById(message.getUserId())) {
            throw new IllegalArgumentException("No such user.");
        }

        if (!channelService.existChannelById(message.getChannelId())) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (data.containsKey(message.getId())) {
            throw new IllegalArgumentException("Message already exists.");
        }

        data.put(message.getId(), message);

    }

    @Override
    public boolean existMessageById(UUID id) {
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
            throw new IllegalArgumentException("No such message.");
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
        return new ArrayList<>(data.values());

    }

    @Override
    public void updateMessage(UUID id, String content, boolean isReply, UUID parentMessageId) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("No such message.");
        }

        if (isReply && (parentMessageId == null || !data.containsKey(parentMessageId))) {
            throw new IllegalArgumentException("No such parent message.");
        }

        if (parentMessageId != null && !isReply) {
            throw new IllegalArgumentException("No reply message.");
        }

        data.get(id).update(content, isReply, parentMessageId);

    }

    @Override
    public void deleteMessageById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("No such message.");
        }

        data.remove(id);

    }
}
