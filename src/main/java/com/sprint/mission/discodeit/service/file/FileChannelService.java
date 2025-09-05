package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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
    public void createChannel(ChannelDTO.CreatePublicChannelRequest request) {

        if (request.channelName().isBlank() || request.category() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        Channel channel = new Channel.Builder()
                .channelName(request.channelName())
                .category(request.category())
                .isVoiceChannel(request.isVoiceChannel())
                .build();

        try(FileOutputStream fos = new FileOutputStream(path.resolve( channel.getId() + FILE_EXTENSION).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            //System.out.println(channel.getId());
            oos.writeObject(channel);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create channel.");
        }

    }

    @Override
    public void createPrivateChannel(ChannelDTO.CreatePrivateChannelRequest request) {

        Channel channel = new Channel.Builder()
                .category(request.category())
                .isPrivate(true)
                .build();

        if (existChannelById(channel.getId())) {
            throw new IllegalArgumentException("Channel already exists.");
        }

        if (channel.getChannelName().isBlank() || channel.getCategory() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        try(FileOutputStream fos = new FileOutputStream(path.resolve( channel.getId() + FILE_EXTENSION).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(channel);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create channel.");
        }

    }

    @Override
    public boolean existChannelById(UUID id) {
        return Files.exists(path.resolve(id + FILE_EXTENSION));
    }

    @Override
    public Optional<ChannelDTO.FindChannelResult> findChannelById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Channel channel = (Channel) ois.readObject();

            ChannelDTO.FindChannelResult findChannelResult = ChannelDTO.FindChannelResult.builder()
                    .id(channel.getId())
                    .channelName(channel.getChannelName())
                    .category(channel.getCategory())
                    .isVoiceChannel(channel.isVoiceChannel())
                    .isPrivate(channel.isPrivate())
                    .createdAt(channel.getCreatedAt())
                    .updatedAt(channel.getUpdatedAt())
                    .build();

            return Optional.ofNullable(findChannelResult);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<ChannelDTO.FindChannelResult> findChannelsByUserId(UUID userId) {
        return List.of();
    }

    @Override
    public List<ChannelDTO.FindChannelResult> findAllChannels() {

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
                    .map(channel -> ChannelDTO.FindChannelResult.builder()
                            .id(channel.getId())
                            .channelName(channel.getChannelName())
                            .category(channel.getCategory())
                            .isVoiceChannel(channel.isVoiceChannel())
                            .isPrivate(channel.isPrivate())
                            .createdAt(channel.getCreatedAt())
                            .updatedAt(channel.getUpdatedAt())
                            .build())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @Override
    public void updateChannel(ChannelDTO.UpdateChannelRequest request) {

        if (request.channelName().isBlank() || request.category() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        try (FileOutputStream fos = new FileOutputStream(path.resolve(request.id() + FILE_EXTENSION).toFile());
             FileInputStream fis = new FileInputStream(path.resolve(request.id() + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            Channel channel = (Channel) ois.readObject();

            if (channel.isPrivate()) {
                throw new IllegalArgumentException("Private channel cannot be updated.");
            }

            channel.update(request.channelName(), request.category(), request.isVoiceChannel());
            oos.writeObject(channel);

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to update channel.");
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("No such channel.");
        }

    }

    @Override
    public void deleteChannelById(UUID id) {

        if (!existChannelById(id)) {
            throw new IllegalArgumentException("No such channel.");
        } else {
            try {
                Files.delete(path.resolve(id + FILE_EXTENSION));
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to delete channel.");
            }
        }

    }

}
