package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.JCFChannelService;
import com.sprint.mission.discodeit.service.JCFMessageService;
import com.sprint.mission.discodeit.service.JCFUserService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

        if (!userService.existById(message.getUserId().toString())) {

            System.out.println("No such user.");
            return;

        }

        if (!channelService.existById(message.getChannelId().toString())) {

            System.out.println("No such channel.");
            return;

        }

        data.put(message.getId(), message);

    }

    @Override
    public boolean existById(String id) {
        return data.containsKey(UUID.fromString(id));
    }

    @Override
    public void readById(String id) {

        if (!data.containsKey(UUID.fromString(id))) {

            System.out.println("No such message.");
            return;

        }

        Message message = data.get(UUID.fromString(id));
        System.out.println(message.toString());

    }

    @Override
    public void readAll() {

        System.out.println("Message List:");

        data.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getValue().getContent());
        });

    }

    @Override
    public void update(String id, String content, boolean isReply, UUID parentMessageId) {

        if (!data.containsKey(UUID.fromString(id))) {

            System.out.println("No such message.");
            return;

        }

        data.get(UUID.fromString(id)).update(content, isReply, parentMessageId);

    }

    @Override
    public void deleteById(String id) {

        if (!data.containsKey(UUID.fromString(id))) {

            System.out.println("No such message.");
            return;

        }

        data.remove(UUID.fromString(id));

    }
}
