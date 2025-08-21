package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class FileUserService implements UserService {

    private final Path path;
    private static final String FILE_EXTENSION = ".ser";

    public FileUserService(Path path) {

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
    public void createUser(User user) {

        if (existUserById(user.getId())) {
            throw new IllegalArgumentException("User already exists.");
        }

        if (user.getPassword().isBlank() || user.getEmail().isBlank() || user.getNickname().isBlank()) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        if (existUserByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        try(FileOutputStream fos = new FileOutputStream(path.resolve(user.getId() + FILE_EXTENSION).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(user);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create user.");
        }

    }

    @Override
    public boolean existUserById(UUID id) {

        return Files.exists(path.resolve(id + FILE_EXTENSION));
    }

    @Override
    public boolean existUserByEmail(String email) {

        for (User existingUser : findAllUsers()) {
            if (existingUser.getEmail().equals(email)) {
                return true;
            }
        }

        return false;

    }

    @Override
    public Optional<User> findUserById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            User user = (User) ois.readObject();

            return Optional.ofNullable(user);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        if (!existUserByEmail(email)) {
            return Optional.empty();
        }

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
    public List<User> findAllUsers() {

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
            return new ArrayList<>();
        }

    }

    @Override
    public void updateUser(UUID id, String nickname, String email, String password, String description) {

        User user = findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such user."));

        if (email.isBlank() || password.isBlank() || nickname.isBlank()) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        if (existUserByEmail(email) && !user.getEmail().equals(email)) {
            throw new IllegalArgumentException("Email already exists.");
        }

        try (FileOutputStream fos = new FileOutputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            user.update(nickname, email, password, description);
            oos.writeObject(user);

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to update user.");
        }

    }

    @Override
    public void deleteUserById(UUID id) {

        if (!existUserById(id)) {
            throw new IllegalArgumentException("No such user.");
        } else {
            try {
                Files.delete(path.resolve(id + FILE_EXTENSION));
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to delete user.");
            }
        }

    }
}
