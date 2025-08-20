package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
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
        this.path = path;
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public void create(Message message) {

        if (!userService.existById(message.getUserId())) {
            throw new IllegalArgumentException("No such user.");
        }

        if (!channelService.existById(message.getChannelId())) {
            throw new IllegalArgumentException("No such channel.");
        }

        try(FileOutputStream fos = new FileOutputStream(path.resolve( message.getId() + FILE_EXTENSION).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public boolean existById(UUID id) {
        return Files.exists(path.resolve(id + FILE_EXTENSION));
    }

    @Override
    public Optional<Message> readById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Message message = (Message) ois.readObject();

            return Optional.ofNullable(message);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Message> readChildrenById(UUID id) {

        if (!existById(id)) {
            throw new IllegalArgumentException();
        }

        System.out.println(readAll().size());

        List<Message> childMessageList = readAll().stream()
                .filter(message -> message.isReply() && message.getParentMessageId()
                        .equals(id)).toList();

        System.out.println("답글 수 : " + childMessageList.size());

        return childMessageList;

    }

    @Override
    public List<Message> readAll() {

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
    public void update(UUID id, String content, boolean isReply, UUID parentMessageId) {

        Message message = readById(id).orElseThrow();

        try (FileOutputStream fos = new FileOutputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            message.update(content, isReply, parentMessageId);
            oos.writeObject(message);

        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void deleteById(UUID id) {

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
