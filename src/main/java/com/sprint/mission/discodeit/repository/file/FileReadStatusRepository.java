package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
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
public class FileReadStatusRepository implements ReadStatusRepository {

    @Value("${file.upload.path}")
    private String fileUploadFolder;
    @Value("${file.upload.folder.readStatus.name}")
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
    public void save(ReadStatus readStatus) {

        try (FileOutputStream fos = new FileOutputStream(path.resolve(readStatus.getId() + fileExtension).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(readStatus);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void saveAll(Iterable<ReadStatus> readStatus) {
        readStatus.forEach(this::save);
    }

    @Override
    public boolean existById(UUID id) {
        return Files.exists(path.resolve(id + fileExtension));
    }

    @Override
    public boolean existByUserIdAndChannelId(UUID userId, UUID channelId) {
        return findAll().stream().anyMatch(status -> status.getUserId().equals(userId) && status.getChannelId().equals(channelId));
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + fileExtension).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            ReadStatus readStatus = (ReadStatus) ois.readObject();

            return Optional.ofNullable(readStatus);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId) {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (ReadStatus) data;
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(status -> status.getUserId().equals(userId) && status.getChannelId().equals(channelId))
                    .findFirst();
        } catch (IOException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<ReadStatus> findByUserId(UUID userId) {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (ReadStatus) data;
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(status -> status.getUserId().equals(userId))
                    .toList();
        } catch (IOException e) {
            return new ArrayList<>();
        }

    }

    @Override
    public List<ReadStatus> findByChannelId(UUID channelId) {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (ReadStatus) data;
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(status -> status.getChannelId().equals(channelId))
                    .toList();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ReadStatus> findAll() {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (ReadStatus) data;
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
    public void deleteByUserIdAndChannelId(UUID userId, UUID channelId) {

        try (Stream<Path> pathStream = Files.list(path)) {
            pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (ReadStatus) data;
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(status -> status.getUserId().equals(userId) && status.getChannelId().equals(channelId))
                    .forEach(status -> deleteById(status.getId()));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void deleteByUserId(UUID userId) {


        try (Stream<Path> pathStream = Files.list(path)) {
            pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (ReadStatus) data;
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(status -> status.getUserId().equals(userId))
                    .forEach(status -> deleteById(status.getId()));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void deleteByChannelId(UUID channelId) {

        try (Stream<Path> pathStream = Files.list(path)) {
            pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (ReadStatus) data;
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(status -> status.getChannelId().equals(channelId))
                    .forEach(status -> deleteById(status.getId()));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void deleteAllByIdIn(Iterable<UUID> idList) {
        idList.forEach(this::deleteById);
    }
}
