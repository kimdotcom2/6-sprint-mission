package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class FileMessageService implements MessageService {

    private final Path path;
    private static final String FILE_EXTENSION = ".ser";
    private final UserService userService;
    private final ChannelService channelService;

    public FileMessageService(Path path, UserService userService, ChannelService channelService) {

        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new IllegalArgumentException("Invalid path");
            }
        }

        this.path = path;
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public void createMessage(MessageDTO.CreateMessageRequest request) {

        if (!userService.existUserById(request.userId())) {
            throw new IllegalArgumentException("No such user.");
        }

        if (!channelService.existChannelById(request.channelId())) {
            throw new IllegalArgumentException("No such channel.");
        }

        Message message = new Message.Builder()
                .userId(request.userId())
                .channelId(request.channelId())
                .content(request.content())
                .isReply(request.isReply())
                .parentMessageId(request.parentMessageId())
                .build();

        try(FileOutputStream fos = new FileOutputStream(path.resolve( message.getId() + FILE_EXTENSION).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create message.");
        }

    }

    @Override
    public boolean existMessageById(UUID id) {
        return Files.exists(path.resolve(id + FILE_EXTENSION));
    }

    @Override
    public Optional<MessageDTO.FindMessageResult> findMessageById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Message message = (Message) ois.readObject();

            return Optional.ofNullable(MessageDTO.FindMessageResult.builder()
                    .id(message.getId())
                    .content(message.getContent())
                    .isReply(message.isReply())
                    .parentMessageId(message.getParentMessageId())
                    .channelId(message.getChannelId())
                    .userId(message.getUserId())
                    .createdAt(message.getCreatedAt())
                    .updatedAt(message.getUpdatedAt())
                    .build());

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<MessageDTO.FindMessageResult> findChildMessagesById(UUID id) {

        if (!existMessageById(id)) {
            throw new IllegalArgumentException("No such message.");
        }

        return findAllMessages().stream()
                .filter(message -> message.isReply() && message.parentMessageId() != null && message.parentMessageId().equals(id))
                .map(message -> MessageDTO.FindMessageResult.builder()
                        .id(message.id())
                        .content(message.content())
                        .isReply(message.isReply())
                        .parentMessageId(message.parentMessageId())
                        .channelId(message.channelId())
                        .userId(message.userId())
                        .createdAt(message.createdAt())
                        .updatedAt(message.updatedAt())
                        .build())
                .toList();

    }

    @Override
    public List<MessageDTO.FindMessageResult> findMessagesByUserId(UUID userId) {

        if (!userService.existUserById(userId)) {
            throw new IllegalArgumentException("No such user.");
        }

        return findAllMessages().stream()
                .filter(message -> message.id().equals(userId))
                .map(message -> MessageDTO.FindMessageResult.builder()
                        .id(message.id())
                        .content(message.content())
                        .isReply(message.isReply())
                        .parentMessageId(message.parentMessageId())
                        .channelId(message.channelId())
                        .userId(message.userId())
                        .createdAt(message.createdAt())
                        .updatedAt(message.updatedAt())
                        .build())
                .toList();

    }

    @Override
    public List<MessageDTO.FindMessageResult> findMessagesByChannelId(UUID channelId) {

        if (!channelService.existChannelById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        return findAllMessages().stream()
                .filter(message -> message.channelId().equals(channelId))
                .sorted(Comparator.comparing(MessageDTO.FindMessageResult::createdAt))
                .map(message -> MessageDTO.FindMessageResult.builder()
                        .id(message.id())
                        .content(message.content())
                        .isReply(message.isReply())
                        .parentMessageId(message.parentMessageId())
                        .channelId(message.channelId())
                        .userId(message.userId())
                        .createdAt(message.createdAt())
                        .updatedAt(message.updatedAt())
                        .build())
                .toList();

    }

    @Override
    public List<MessageDTO.FindMessageResult> findAllMessages() {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(path -> path.toString().endsWith(FILE_EXTENSION))
                    .map(path -> {
                        try (
                                FileInputStream fis = new FileInputStream(path.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (Message) data;
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .map(message -> MessageDTO.FindMessageResult.builder()
                            .id(message.getId())
                            .content(message.getContent())
                            .isReply(message.isReply())
                            .parentMessageId(message.getParentMessageId())
                            .channelId(message.getChannelId())
                            .userId(message.getUserId())
                            .createdAt(message.getCreatedAt())
                            .updatedAt(message.getUpdatedAt())
                            .build())
                    .toList();
        } catch (IOException e) {
            return new ArrayList<>();
        }

    }

    @Override
    public void updateMessage(MessageDTO.UpdateMessageRequest request) {

        if (request.isReply() && (request.parentMessageId() == null || !existMessageById(request.parentMessageId()))) {
            throw new IllegalArgumentException("No such parent message.");
        }

        if (request.parentMessageId() != null && !request.isReply()) {
            throw new IllegalArgumentException("No reply message.");
        }

        try (FileOutputStream fos = new FileOutputStream(path.resolve(request.id() + FILE_EXTENSION).toFile());
             FileInputStream fis = new FileInputStream(path.resolve(request.id() + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            Message message = (Message) ois.readObject();

            message.update(request.content(), request.isReply(), request.parentMessageId());
            oos.writeObject(message);

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to update message.");
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("No such message.");
        }

    }

    @Override
    public void deleteMessageById(UUID id) {

        if (!existMessageById(id)) {
            throw new IllegalArgumentException("No such message.");
        } else {
            try {
                Files.delete(path.resolve(id + FILE_EXTENSION));
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to delete message.");
            }
        }

    }
}
