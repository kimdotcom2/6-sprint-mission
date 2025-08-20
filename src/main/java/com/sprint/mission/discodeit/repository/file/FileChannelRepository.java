package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

public class FileChannelRepository implements ChannelRepository {

    private final Path path;
    private final String extension;

    public FileChannelRepository(Path path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    @Override
    public void save(Channel channel) {

        try(FileOutputStream fos = new FileOutputStream(path.resolve( channel.getId() + extension).toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(channel);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void saveAll(Iterable<Channel> channels) {
        channels.forEach(this::save);
    }
}
