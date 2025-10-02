package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.BinaryContentDTO.BinaryContent;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import jakarta.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "discodeit.storage", name = "type", havingValue = "local")
public class LocalBinaryContentStorage implements BinaryContentStorage {


  @Value("${discodeit.storage.local.root-path}")
  private Path root;

  @PostConstruct
  private void init() {

    if (!root.toFile().exists()) {
      try {
        Files.createDirectories(root);
      } catch (IOException e) {
        throw new IllegalArgumentException("폴더가 존재하지 않습니다.");
      }
    }

  }

  private Path resolvePath(UUID id) {
    return root.resolve(id.toString());
  }

  @Override
  public UUID put(UUID id, byte[] bytes) {

    try(FileOutputStream fos = new FileOutputStream(String.valueOf(resolvePath(id).toFile()));
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(bytes);
    } catch (IOException e) {
      throw new IllegalArgumentException("파일을 저장할 수 없습니다.");
    }

    return id;

  }

  @Override
  public void deleteById(UUID id) {

    try {
      Files.deleteIfExists(resolvePath(id));
    } catch (IOException e) {
      throw new IllegalArgumentException("파일을 삭제할 수 없습니다.");
    }

  }

  @Override
  public InputStream get(UUID id) {

    try(InputStream is = Files.newInputStream(resolvePath(id))) {
      return is;
    } catch (IOException e) {
      throw new NoSuchDataException("No such file.");
    }

  }

  @Override
  public ResponseEntity<BinaryContentDTO.BinaryContent> download(BinaryContent binaryContentDTO) {
    return ResponseEntity.ok()
        .header("Content-Disposition", "attachment; filename=\"" + binaryContentDTO.getFileName() + "\"")
        .body(binaryContentDTO);
  }
}
