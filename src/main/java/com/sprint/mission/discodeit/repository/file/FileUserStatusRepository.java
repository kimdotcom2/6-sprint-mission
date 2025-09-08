package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository("fileUserStatusRepository")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "code.repository.type", havingValue = "file")
public class FileUserStatusRepository implements UserStatusRepository {

    @Value("${file.upload.path}")
    private String fileUploadFolder;
    @Value("${file.upload.folder.userStatus.name}")
    private String folderName;
    @Value("${file.upload.extension}")
    private String fileExtension;

    private Path initFolder() {

        Path path = Path.of(fileUploadFolder + folderName);

        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to create directory.");
            }
        }

        return path;

    }

    @Override
    public void save(UserStatus userStatus) {

        Path path = initFolder();

        try(FileOutputStream fos = new FileOutputStream(path.resolve(userStatus.getId() + fileExtension).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(userStatus);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void saveAll(Iterable<UserStatus> userStatus) {
        userStatus.forEach(this::save);
    }

    @Override
    public boolean existById(UUID id) {
        Path path = initFolder();

        return Files.exists(path.resolve(id + fileExtension));
    }

    @Override
    public boolean existByUserId(UUID userId) {
        return findAll().stream().anyMatch(status -> status.getUserId().equals(userId));
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {

        Path path = initFolder();

        try (FileInputStream fis = new FileInputStream(path.resolve(id + fileExtension).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            UserStatus userStatus = (UserStatus) ois.readObject();

            return Optional.ofNullable(userStatus);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {

        Path path = initFolder();

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (UserStatus) data;
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(status -> status.getUserId().equals(userId))
                    .findFirst();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserStatus> findAll() {
        Path path = initFolder();

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (UserStatus) data;
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

        Path path = initFolder();

        try {
            Files.deleteIfExists(path.resolve(id + fileExtension));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void deleteByUserId(UUID userId) {

        Path path = initFolder();

        List<UserStatus> userStatusList = findAll();

        userStatusList.forEach(status -> {
            if (status.getUserId().equals(userId)) {
                try {
                    Files.deleteIfExists(path.resolve(status.getId() + fileExtension));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @Override
    public void deleteAllByIdIn(Iterable<UUID> idList) {

        idList.forEach(this::deleteById);

    }
}
