package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static com.sprint.mission.discodeit.config.PathConfig.FILE_PATH;

public class FileMessageRepository implements MessageRepository {

    private final Path path;
    private static final String FILE_EXTENSION = ".ser";
    private final String folderName;

    public FileMessageRepository(String folderName) {
        this.folderName = folderName;
        path = Path.of(FILE_PATH + folderName);

        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to create directory.");
            }
        }
    }

    @Override
    public void save(Message message) {

        try(FileOutputStream fos = new FileOutputStream(path.resolve( message.getId() + FILE_EXTENSION).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void saveAll(Iterable<Message> messages) {
        messages.forEach(this::save);
    }

    @Override
    public boolean existById(UUID id) {
        return Files.exists(path.resolve(id + FILE_EXTENSION));
    }

    @Override
    public Optional<Message> findById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Message message = (Message) ois.readObject();

            return Optional.ofNullable(message);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Message> findChildById(UUID id) {
        return findAll().stream()
                .filter(Message::isReply)
                .filter(message -> message.getParentMessageId() != null && message.getParentMessageId().equals(id))
                .toList();
    }

    @Override
    public List<Message> findByUserId(UUID userId) {
        return findAll().stream()
                .filter(message -> message.getUserId().equals(userId))
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .toList();
    }

    @Override
    public List<Message> findByChannelId(UUID channelId) {
        return findAll().stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .toList();
    }

    @Override
    public List<Message> findAll() {

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
            throw new RuntimeException();
        }

    }

    @Override
    public void deleteById(UUID id) {

        try {
            Files.deleteIfExists(path.resolve(id + FILE_EXTENSION));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }
}
