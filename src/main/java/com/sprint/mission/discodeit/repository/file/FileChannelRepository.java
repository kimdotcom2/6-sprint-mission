package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository("fileChannelRepository")
@RequiredArgsConstructor
public class FileChannelRepository implements ChannelRepository {

    @Value("${file.upload.path}")
    private String fileUploadFolder;
    @Value("${file.upload.folder.channel.name}")
    private String folderName;
    @Value("${file.upload.extension}")
    private String fileExtension;

    private Path initFolder() {

        Path path = Path.of(fileUploadFolder + folderName);

        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to create directory.");
            }
        }

        return path;

    }

    /*private final Path path;
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

    }*/

    @Override
    public void save(Channel channel) {

        Path path = initFolder();

        try(FileOutputStream fos = new FileOutputStream(path.resolve( channel.getId() + fileExtension).toFile());
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

        Path path = initFolder();

        return Files.exists(path.resolve(id + fileExtension));
    }

    @Override
    public Optional<Channel> findById(UUID id) {

        Path path = initFolder();

        try (FileInputStream fis = new FileInputStream(path.resolve(id + fileExtension).toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Channel channel = (Channel) ois.readObject();

            return Optional.ofNullable(channel);

        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }

    /*@Override
    public List<Channel> findByUserId(UUID userId) {
        return findAll().stream()
                .filter(channel -> channel
                        .getUserMap().containsKey(userId))
                .toList();
    }*/

    @Override
    public List<Channel> findAll() {

        Path path = initFolder();

        try (Stream<Path> pathStream = Files.list(path)) {
            return pathStream
                    .filter(file -> file.toString().endsWith(fileExtension))
                    .map(file -> {
                        try (
                                FileInputStream fis = new FileInputStream(file.toFile());
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

        Path path = initFolder();

        try {
            Files.deleteIfExists(path.resolve(id + fileExtension));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }
}
