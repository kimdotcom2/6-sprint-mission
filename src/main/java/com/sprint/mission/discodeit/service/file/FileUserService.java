package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.utils.SecurityUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class FileUserService implements UserService {

    private final Path path;
    private static final String FILE_EXTENSION = ".ser";
    private final SecurityUtil securityUtil = new SecurityUtil();

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
    public void createUser(UserDTO.CreateUserCommand request) {

        if (existUserByEmail(request.email()) || existUserByNickname(request.nickname())) {
            throw new IllegalArgumentException("User already exists.");
        }

        User user = new User.Builder()
                .email(request.email())
                .password(request.password())
                .nickname(request.nickname())
                .description(request.description())
                .build();

        user.updatePassword(securityUtil.hashPassword(user.getPassword()));

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

        for (UserDTO.FindUserResult existingUser : findAllUsers()) {
            if (existingUser.email().equals(email)) {
                return true;
            }
        }

        return false;

    }

    @Override
    public boolean existUserByNickname(String nickname) {

        for (UserDTO.FindUserResult existingUser : findAllUsers()) {
            if (existingUser.nickname().equals(nickname)) {
                return true;
            }
        }

        return false;

    }

    public Optional<User> parseUserById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            User user = (User) ois.readObject();

            return Optional.ofNullable(user);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            User user = (User) ois.readObject();

            return Optional.ofNullable(UserDTO.FindUserResult.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .description(user.getDescription())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build());

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserByEmail(String email) {

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
                    .map(user -> UserDTO.FindUserResult.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .nickname(user.getNickname())
                            .description(user.getDescription())
                            .createdAt(user.getCreatedAt())
                            .updatedAt(user.getUpdatedAt())
                            .build())
                    .findFirst();
        } catch (IOException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserByNickname(String nickname) {

        if (existUserByNickname(nickname)) {
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
                    .filter(user -> user.getNickname().equals(nickname))
                    .map(user -> UserDTO.FindUserResult.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .nickname(user.getNickname())
                            .description(user.getDescription())
                            .createdAt(user.getCreatedAt())
                            .updatedAt(user.getUpdatedAt())
                            .build())
                    .findFirst();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserDTO.FindUserResult> findAllUsers() {

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
                    .map(user -> UserDTO.FindUserResult.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .nickname(user.getNickname())
                            .description(user.getDescription())
                            .createdAt(user.getCreatedAt())
                            .updatedAt(user.getUpdatedAt())
                            .build())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    @Override
    public void updateUser(UserDTO.UpdateUserCommand request) {

        User user = parseUserById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("No such user."));

        if (existUserByEmail(request.email()) && !user.getEmail().equals(request.email())) {
            throw new IllegalArgumentException("Email already exists.");
        }
        
        if (!securityUtil.hashPassword(request.currentPassword()).equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        try (FileOutputStream fos = new FileOutputStream(path.resolve(request.id() + FILE_EXTENSION).toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            user.update(request.nickname(), request.email(), securityUtil.hashPassword(request.newPassword()), request.description());
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
