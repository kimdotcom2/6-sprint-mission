package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface BinaryContentStorage {

  UUID put(UUID id, byte[] bytes);

  void deleteById(UUID id);

  InputStream get(UUID id);

  ResponseEntity<?> download(BinaryContentDTO.BinaryContent binaryContentDTO);

}
