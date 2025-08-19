package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.JCFUserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUserCrudService implements JCFUserService {

    private final Path path;

    public FileUserCrudService(Path path) {

        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.path = path;
    }

    @Override
    public void create(User user) {

        try(FileOutputStream fos = new FileOutputStream(path.toString() + "\\" + user.getId().toString() + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);) {

            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean existById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.toString() + "\\" + id.toString() + ".ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            User user = (User) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    public User readById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.toString() + "\\" + id.toString() + ".ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            User user = (User) ois.readObject();

            //System.out.println(user.toString());
            return user;

        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public List<User> readAll() {

        if (Files.exists(path)) {
            try {
                List<User> list = Files.list(path)
                        .map(path -> {
                            try (
                                    FileInputStream fis = new FileInputStream(path.toFile());
                                    ObjectInputStream ois = new ObjectInputStream(fis)
                            ) {
                                Object data = ois.readObject();
                                return (User) data;
                            } catch (IOException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList();
                return list;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public void update(UUID id, String nickname, String email, String password, String description) {

    }

    @Override
    public void deleteById(UUID id) {

    }
}
