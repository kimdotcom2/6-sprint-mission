package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class FileUserCrudService implements UserService {

    private final Path path;
    private static final String FILE_EXTENSION = ".ser";

    public FileUserCrudService(Path path) {

        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new IllegalArgumentException();
            }
        }

        this.path = path;
    }

    @Override
    public void create(User user) {

        try(FileOutputStream fos = new FileOutputStream(path.resolve(user.getId() + FILE_EXTENSION).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            //System.out.println(user.getId());
            oos.writeObject(user);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public boolean existById(UUID id) {

        return Files.exists(path.resolve(id + FILE_EXTENSION));
    }

    @Override
    public Optional<User> readById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            User user = (User) ois.readObject();

            return Optional.ofNullable(user);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<User> readAll() {

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
    public void update(UUID id, String nickname, String email, String password, String description) {

        User user = readById(id).orElseThrow(IllegalArgumentException::new);

        try (FileOutputStream fos = new FileOutputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            user.update(nickname, email, password, description);
            oos.writeObject(user);

        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void deleteById(UUID id) {

        File file = path.resolve(id + FILE_EXTENSION).toFile();

        if (file.exists()) {

            if (!file.delete()) {
                throw new IllegalArgumentException();
            }

        } else {
            throw new IllegalArgumentException();
        }

    }
}
