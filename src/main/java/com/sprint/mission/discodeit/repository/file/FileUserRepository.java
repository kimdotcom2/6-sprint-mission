package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import jakarta.annotation.PostConstruct;
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

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "code.repository.type", havingValue = "file")
public class FileUserRepository implements UserRepository {

    @Value("${file.upload.path}")
    private String fileUploadFolder;
    @Value("${file.upload.folder.user.name}")
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
    public void save(User user) {

        try(FileOutputStream fos = new FileOutputStream(path.resolve(user.getId() + fileExtension).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(user);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void saveAll(Iterable<User> users) {
        users.forEach(this::save);
    }

    @Override
    public boolean existById(UUID id) {
        return Files.exists(path.resolve(id + fileExtension));
    }

    @Override
    public boolean existByEmail(String email) {
        return findAll().stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean existByNickname(String nickname) {
        return findAll().stream().anyMatch(user -> user.getNickname().equals(nickname));
    }

    @Override
    public boolean existByEmailOrNickname(String email, String nickname) {
        return findAll().stream().anyMatch(user -> user.getEmail().equals(email) || user.getNickname().equals(nickname));
    }

    @Override
    public Optional<User> findById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + fileExtension).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            User user = (User) ois.readObject();

            return Optional.ofNullable(user);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<User> findByEmail(String email) {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (User) data;
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(user -> user.getEmail().equals(email))
                    .findFirst();
        } catch (IOException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<User> findByNickname(String nickname) {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (User) data;
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(user -> user.getNickname().equals(nickname))
                    .findFirst();
        } catch (IOException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<User> findAll() {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (User) data;
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
}
