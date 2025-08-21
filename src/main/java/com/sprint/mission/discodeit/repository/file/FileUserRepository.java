package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class FileUserRepository implements UserRepository {

    private final Path path;
    private final String extension;

    public FileUserRepository(Path path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    @Override
    public void save(User user) {

        try(FileOutputStream fos = new FileOutputStream(path.resolve(user.getId() + extension).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(user);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void saveAll(Iterable<User> users) {
        users.forEach(this::save);
    }

    @Override
    public boolean existById(UUID id) {
        return Files.exists(path.resolve(id + extension));
    }

    @Override
    public Optional<User> findById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + extension).toFile());
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
                    .filter(path -> path.toString().endsWith(extension))
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
                    .filter(path -> path.toString().endsWith(extension))
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
