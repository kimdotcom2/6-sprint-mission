package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class FileChannelService implements ChannelService {

    private final Path path;
    private static final String FILE_EXTENSION = ".ser";

    public FileChannelService(Path path) {

        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new IllegalArgumentException("Invalid path");
            }
        }

        this.path = path;
    }

    @Override
    public void createChannel(Channel channel) {

        if (existChannelById(channel.getId())) {
            throw new IllegalArgumentException("Channel already exists.");
        }

        if (channel.getChannelName().isBlank() || channel.getCategory().isBlank()) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        try(FileOutputStream fos = new FileOutputStream(path.resolve( channel.getId() + FILE_EXTENSION).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            //System.out.println(channel.getId());
            oos.writeObject(channel);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create channel.");
        }

    }

    @Override
    public void addUserToChannel(UUID channelId, User user) {

        Channel channel = findChannelById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));
        channel.getUserMap().put(user.getId(), user);

        try (FileOutputStream fos = new FileOutputStream(path.resolve(channelId + FILE_EXTENSION).toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(channel);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to add user to channel.");
        }

    }

    @Override
    public void addMessageToChannel(UUID channelId, Message message) {

        Channel channel = findChannelById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));
        channel.getMessageMap().put(message.getId(), message);

        try (FileOutputStream fos = new FileOutputStream(path.resolve(channelId + FILE_EXTENSION).toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(channel);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to add message to channel.");
        }

    }

    @Override
    public boolean existChannelById(UUID id) {
        return Files.exists(path.resolve(id + FILE_EXTENSION));
    }

    @Override
    public Optional<Channel> findChannelById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Channel channel = (Channel) ois.readObject();

            return Optional.ofNullable(channel);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Channel> findAllChannels() {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(path -> path.toString().endsWith(FILE_EXTENSION))
                    .map(path -> {
                        try (
                                FileInputStream fis = new FileInputStream(path.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (Channel) data;
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();
        } catch (IOException e) {
            return new ArrayList<>();
        }

    }

    @Override
    public void updateChannel(UUID id, String channelName, String category, boolean isVoiceChannel) {

        Channel channel = findChannelById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));

        if (channelName.isBlank() || category.isBlank()) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        try (FileOutputStream fos = new FileOutputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            channel.update(channelName, category, isVoiceChannel);
            oos.writeObject(channel);

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to update channel.");
        }

    }

    @Override
    public void deleteChannelById(UUID id) {

        File file = path.resolve(id + FILE_EXTENSION).toFile();

        if (file.exists()) {

            if (!file.delete()) {
                throw new IllegalArgumentException("Failed to delete file.");
            }

        } else {
            throw new IllegalArgumentException("No such channel.");
        }

    }

    @Override
    public void deleteUserFromChannel(UUID channelId, UUID userId) {

        Channel channel = findChannelById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));
        channel.getUserMap().remove(userId);

        try (FileOutputStream fos = new FileOutputStream(path.resolve(channelId + FILE_EXTENSION).toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(channel);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to delete user from channel.");
        }

    }

    @Override
    public void deleteMessageFromChannel(UUID channelId, UUID messageId) {

        Channel channel = findChannelById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));
        channel.getMessageMap().remove(messageId);

        try (FileOutputStream fos = new FileOutputStream(path.resolve(channelId + FILE_EXTENSION).toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(channel);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to delete message from channel.");
        }

    }
}
