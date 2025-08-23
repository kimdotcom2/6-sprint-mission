package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static com.sprint.mission.discodeit.config.PathConfig.FILE_PATH;

public class FileChannelRepository implements ChannelRepository {

    private final Path path;
    private static final String FILE_EXTENSION = ".ser";
    private final String folderName;

    public FileChannelRepository(String folderName) {
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
    public void save(Channel channel) {

        try(FileOutputStream fos = new FileOutputStream(path.resolve( channel.getId() + FILE_EXTENSION).toFile());
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

    @Override
    public boolean existById(UUID id) {
        return Files.exists(path.resolve(id + FILE_EXTENSION));
    }

    @Override
    public Optional<Channel> findById(UUID id) {

        try (FileInputStream fis = new FileInputStream(path.resolve(id + FILE_EXTENSION).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Channel channel = (Channel) ois.readObject();

            return Optional.ofNullable(channel);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Channel> findByUserId(UUID userId) {
        return findAll().stream()
                .filter(channel -> channel.getUserMap().containsKey(userId))
                .toList();
    }

    @Override
    public List<Channel> findAll() {

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(path -> path.toString().endsWith(FILE_EXTENSION))
                    .map(path -> {
                        try (
                                FileInputStream fis = new FileInputStream(path.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (Channel) data;
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
