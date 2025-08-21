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

    private final Path path = Path.of(FILE_PATH);
    private final String extension;

    public FileMessageRepository(String extension) {
        this.extension = extension;
    }

    @Override
    public void save(Message message) {

        try(FileOutputStream fos = new FileOutputStream(path.resolve( message.getId() + extension).toFile());
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
        return Files.exists(path.resolve(id + extension));
    }

    @Override
    public Optional<Message> findById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + extension).toFile());
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
                .filter(message -> message.getParentMessageId().equals(id))
                .toList();
    }

    @Override
    public List<Message> findAll() {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(path -> path.toString().endsWith(extension))
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
    public void deleteById(UUID id) {

        try {
            Files.deleteIfExists(path.resolve(id + extension));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }
}
