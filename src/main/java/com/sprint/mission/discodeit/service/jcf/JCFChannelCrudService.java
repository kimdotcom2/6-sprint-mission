package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.JCFChannelService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JCFChannelCrudService implements JCFChannelService {

    private final Map<UUID, Channel> data;

    public JCFChannelCrudService() {
        this.data = new HashMap<>();
    }

    @Override
    public void create(Channel channel) {

        data.put(channel.getId(), channel);

    }

    @Override
    public boolean existById(String id) {
        return data.containsKey(UUID.fromString(id));
    }

    @Override
    public void readById(String id) {

        if (!data.containsKey(UUID.fromString(id))) {

            System.out.println("No such channel.");
            return;

        }

        Channel channel = data.get(UUID.fromString(id));
        System.out.println(channel.toString());

    }

    @Override
    public void readAll() {

        System.out.println("Channel List:");

        data.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getValue().toString());
        });

    }

    @Override
    public void update(String id, String channelName, String category, boolean isVoiceChannel) {

        if (!data.containsKey(UUID.fromString(id))) {

            System.out.println("No such channel.");
            return;

        }

        data.get(UUID.fromString(id)).update(channelName, category, isVoiceChannel);

    }

    @Override
    public void deleteById(String id) {

        if (!data.containsKey(UUID.fromString(id))) {

            System.out.println("No such channel.");
            return;

        }

        data.remove(UUID.fromString(id));

    }
}
