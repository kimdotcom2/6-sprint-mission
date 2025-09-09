package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "code.repository.type", havingValue = "file")
public class FileMessageRepository implements MessageRepository {

    @Value("${file.upload.path}")
    private String fileUploadFolder;
    @Value("${file.upload.folder.message.name}")
    private String folderName;
    @Value("${file.upload.extension}")
    private String fileExtension;

    private Path path;

    @PostConstruct
    private void initFolder() {

        Path path = Path.of(fileUploadFolder + folderName);

        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to create directory.");
            }
        }

        this.path = path;

    }

    @Override
    public void save(Message message) {

        try(FileOutputStream fos = new FileOutputStream(path.resolve( message.getId() + fileExtension).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void saveAll(Iterable<Message> messages) {
        messages.forEach(this::save);
    }

    @Override
    public boolean existById(UUID id) {
        return Files.exists(path.resolve(id + fileExtension));
    }

    @Override
    public Optional<Message> findById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + fileExtension).toFile());
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
                .filter(message -> message.getParentMessageId().equals(id))
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
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
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
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteById(UUID id) {

        try {
            Files.deleteIfExists(path.resolve(id + fileExtension));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void deleteByChannelId(UUID channelId) {
        findByChannelId(channelId).forEach(message -> deleteById(message.getId()));
    }
}
