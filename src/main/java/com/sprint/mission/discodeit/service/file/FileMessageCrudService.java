package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class FileMessageCrudService implements MessageService {

    private final Path path;
    private static final String FILE_EXTENSION = ".ser";
    private final UserService userService;
    private final ChannelService channelService;

    public FileMessageCrudService(Path path, UserService userService, ChannelService channelService) {

        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new IllegalArgumentException();
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
            throw new IllegalArgumentException();
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
            throw new IllegalArgumentException();
        }

        System.out.println(findAllMessages().size());

        List<Message> childMessageList = findAllMessages().stream()
                .filter(message -> message.isReply() && message.getParentMessageId()
                        .equals(id)).toList();

        System.out.println("답글 수 : " + childMessageList.size());

        return childMessageList;

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
    public void updateMessage(UUID id, String content, boolean isReply, UUID parentMessageId) {

        Message message = findMessageById(id).orElseThrow(IllegalArgumentException::new);

        if (existMessageById(parentMessageId) && !message.isReply()) {
            throw new IllegalArgumentException();
        }

        try (FileOutputStream fos = new FileOutputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            message.update(content, isReply, parentMessageId);
            oos.writeObject(message);

        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void deleteMessageById(UUID id) {

        File file = path.resolve(id + FILE_EXTENSION).toFile();

        if (file.exists()) {

            if (!file.delete()) {
                throw new IllegalArgumentException();
            }

        } else {
            throw new IllegalArgumentException();
        }

    }
}
