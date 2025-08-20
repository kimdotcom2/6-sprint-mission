package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

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
}
