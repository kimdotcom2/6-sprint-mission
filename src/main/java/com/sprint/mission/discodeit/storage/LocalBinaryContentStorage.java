package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.BinaryContentDTO.BinaryContent;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import jakarta.annotation.PostConstruct;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
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

    try (FileOutputStream fos = new FileOutputStream(String.valueOf(resolvePath(id).toFile()));
        BufferedOutputStream bos = new BufferedOutputStream(fos);) {
      bos.write(bytes);
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

    try {
      return Files.newInputStream(resolvePath(id));
    } catch (IOException e) {
      throw new NoSuchDataBaseRecordException("No such file.");
    }

  }

  @Override
  public ResponseEntity<Resource> download(BinaryContent binaryContent) {

    if (binaryContent.getId() == null) {
      throw new IllegalArgumentException("Invalid file id.");
    }

    InputStream inputStream = get(binaryContent.getId());

    return ResponseEntity.ok()
        .header("Content-Disposition",
            "attachment; filename=\"" + binaryContent.getFileName() + ".png")
        .header("Content-Length", String.valueOf(binaryContent.getSize()))
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(new InputStreamResource(inputStream));

  }
}
