package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static com.sprint.mission.discodeit.config.PathConfig.FILE_PATH;

public class FileUserRepository implements UserRepository {

    private final Path path;
    private final String folderName;
    private static final String FILE_EXTENSION = ".ser";

    public FileUserRepository(String folderName) {
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
    public void save(User user) {

        //System.out.println(path.toString());

        try(FileOutputStream fos = new FileOutputStream(path.resolve(user.getId() + FILE_EXTENSION).toFile());
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
        return Files.exists(path.resolve(id + FILE_EXTENSION));
    }

    @Override
    public boolean existByEmail(String email) {
        return findAll().stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public Optional<User> findById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
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
                    .filter(path -> path.toString().endsWith(FILE_EXTENSION))
                    .map(path -> {
                        try (
                                FileInputStream fis = new FileInputStream(path.toFile());
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
    public List<User> findAll() {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(path -> path.toString().endsWith(FILE_EXTENSION))
                    .map(path -> {
                        try (
                                FileInputStream fis = new FileInputStream(path.toFile());
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
