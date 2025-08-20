package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

public class FileMessageRepository implements MessageRepository {

    private final Path path;
    private final String extension;

    public FileMessageRepository(Path path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    @Override
    public void save(Message message) {

        try(FileOutputStream fos = new FileOutputStream(path.resolve( message.getId() + extension).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void saveAll(Iterable<Message> messages) {
        messages.forEach(this::save);
    }
}
