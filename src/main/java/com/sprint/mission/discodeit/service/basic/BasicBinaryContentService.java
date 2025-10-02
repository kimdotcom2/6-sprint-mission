package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.BinaryContentDTO.BinaryContentCreateCommand;
import com.sprint.mission.discodeit.entity.BinaryContentEntity;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.mapper.BinaryContentEntityMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentStorage binaryContentStorage;
    private final BinaryContentEntityMapper binaryContentEntityMapper;

    @Transactional
    @Override
    public BinaryContentDTO.BinaryContent createBinaryContent(BinaryContentCreateCommand request) {

        if (request.data() == null) {
            throw new IllegalArgumentException("Data must not be null");
        }

        if (request.contentType() == null) {
            throw new IllegalArgumentException("FileType must not be null");
        }

        BinaryContentEntity binaryContentEntity = BinaryContentEntity.builder()
            .fileName(request.fileName())
            .size((long) request.data().length)
            .contentType(request.contentType())
            .build();

        binaryContentEntity = binaryContentRepository.save(binaryContentEntity);
        binaryContentStorage.put(binaryContentEntity.getId(), request.data());

        return binaryContentEntityMapper.entityToBinaryContent(binaryContentEntity);

    }

    @Override
    public boolean existBinaryContentById(UUID id) {
        return binaryContentRepository.existById(id);
    }

    @Override
    public Optional<BinaryContentDTO.BinaryContent> findBinaryContentById(UUID id) {

        BinaryContentEntity binaryContentEntity = binaryContentRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("No such binary content"));

        byte[] bytes = null;

        try {
          bytes = binaryContentStorage.get(binaryContentEntity.getId()).readAllBytes();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

      return Optional.ofNullable(BinaryContentDTO.BinaryContent.builder()
                .id(binaryContentEntity.getId())
                .fileName(binaryContentEntity.getFileName())
                .size(binaryContentEntity.getSize())
                .createdAt(binaryContentEntity.getCreatedAt())
                .contentType(binaryContentEntity.getContentType())
                .bytes(bytes)
                .build());
    }

    @Override
    public List<BinaryContentDTO.BinaryContent> findAllBinaryContentByIdIn(List<UUID> uuidList) {
        return binaryContentRepository.findAllByIdIn(uuidList).stream()
                .map(binaryContent -> {
                  try {
                    return BinaryContentDTO.BinaryContent.builder()
                            .id(binaryContent.getId())
                            .fileName(binaryContent.getFileName())
                            .size(binaryContent.getSize())
                            .createdAt(binaryContent.getCreatedAt())
                            .contentType(binaryContent.getContentType())
                            .bytes(binaryContentStorage.get(binaryContent.getId()).readAllBytes())
                            .build();
                  } catch (IOException e) {
                    throw new NoSuchDataException("No such binary content");
                  }
                })
                .toList();
    }

    @Override
    public List<BinaryContentDTO.BinaryContent> findAllBinaryContent() {
        return binaryContentRepository.findAll().stream()
                .map(binaryContent -> {
                  try {
                    return BinaryContentDTO.BinaryContent.builder()
                            .id(binaryContent.getId())
                            .fileName(binaryContent.getFileName())
                            .size(binaryContent.getSize())
                            .createdAt(binaryContent.getCreatedAt())
                            .contentType(binaryContent.getContentType())
                            .bytes(binaryContentStorage.get(binaryContent.getId()).readAllBytes())
                            .build();
                  } catch (IOException e) {
                    throw new NoSuchDataException("No such binary content");
                  }
                })
                .toList();
    }

    @Transactional
    @Override
    public void deleteBinaryContentById(UUID id) {

        if (!binaryContentRepository.existById(id)) {
            throw new NoSuchDataException("No such binary content");
        }

        binaryContentRepository.deleteById(id);

    }
}
