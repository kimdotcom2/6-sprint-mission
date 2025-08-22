package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.dto.DiscordDTO;
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
    public void createMessage(Message message) {

        if (!userService.existUserById(message.getUserId())) {
            throw new IllegalArgumentException("No such user.");
        }

        if (!channelService.existChannelById(message.getChannelId())) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (existMessageById(message.getId())) {
            throw new IllegalArgumentException("Message already exists.");
        }

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
    public Optional<Message> findMessageById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Message message = (Message) ois.readObject();

            return Optional.ofNullable(message);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Message> findChildMessagesById(UUID id) {

        if (!existMessageById(id)) {
            throw new IllegalArgumentException("No such message.");
        }

        //System.out.println(findAllMessages().size());

        //System.out.println("답글 수 : " + childMessageList.size());

        return findAllMessages().stream()
                .filter(message -> message.isReply() && message.getParentMessageId() != null && message.getParentMessageId().equals(id))
                .toList();

    }

    @Override
    public List<Message> findMessagesByUserId(UUID userId) {

        if (!userService.existUserById(userId)) {
            throw new IllegalArgumentException("No such user.");
        }

        return findAllMessages().stream()
                .filter(message -> message.getUserId().equals(userId))
                .toList();

    }

    @Override
    public List<Message> findMessagesByChannelId(UUID channelId) {

        if (!channelService.existChannelById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        return findAllMessages().stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .toList();

    }

    @Override
    public List<Message> findAllMessages() {

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
                    .toList();
        } catch (IOException e) {
            return new ArrayList<>();
        }

    }

    @Override
    public void updateMessage(DiscordDTO.UpdateMessageRequest request) {

        Message message = findMessageById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("No such message."));

        if (request.isReply() && (request.parentMessageId() == null || !existMessageById(request.parentMessageId()))) {
            throw new IllegalArgumentException("No such parent message.");
        }

        if (request.parentMessageId() != null && !request.isReply()) {
            throw new IllegalArgumentException("No reply message.");
        }

        try (FileOutputStream fos = new FileOutputStream(path.resolve(request.id() + FILE_EXTENSION).toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            message.update(request.content(), request.isReply(), request.parentMessageId());
            oos.writeObject(message);

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to update message.");
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
