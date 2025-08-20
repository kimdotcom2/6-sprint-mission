package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelCrudService implements ChannelService {

    private final Map<UUID, Channel> data;

    public JCFChannelCrudService() {
        this.data = new TreeMap<>();
    }

    @Override
    public void create(Channel channel) {

        if (data.containsKey(channel.getId())) {
            throw new IllegalArgumentException("Channel already exists.");
        }

        data.put(channel.getId(), channel);

    }

    @Override
    public void addUserToChannel(UUID channelId, User user) {

        if (!data.containsKey(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        data.get(channelId).getUserMap().put(user.getId(), user);

    }

    @Override
    public void addMessageToChannel(UUID channelId, Message message) {

        if (!data.containsKey(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        data.get(channelId).getMessageMap().put(message.getId(), message);

    }

    @Override
    public boolean existById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public Optional<Channel> findChannelById(UUID id) {

        if (!data.containsKey(id)) {
            return Optional.empty();
        }

        Channel channel = data.get(id);
        //System.out.println(channel.toString());
        return Optional.ofNullable(channel);

    }

    @Override
    public List<Channel> findAllChannels() {

        System.out.println("Channel List:");

        /*data.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getValue().toString());
        });*/
        return new ArrayList<>(data.values());

    }

    @Override
    public void update(UUID id, String channelName, String category, boolean isVoiceChannel) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        data.get(id).update(channelName, category, isVoiceChannel);

    }

    @Override
    public void deleteById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        data.remove(id);

    }

    @Override
    public void deleteUserFromChannel(UUID channelId, UUID userId) {

        if (!data.containsKey(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        data.get(channelId).getUserMap().remove(userId);

    }

    @Override
    public void deleteMessageFromChannel(UUID channelId, UUID messageId) {

        if (!data.containsKey(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        data.get(channelId).getMessageMap().remove(messageId);

    }
}
