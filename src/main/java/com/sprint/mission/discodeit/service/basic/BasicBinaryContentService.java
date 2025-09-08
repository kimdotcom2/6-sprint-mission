package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("basicBinaryContentService")
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {

    private final BinaryContentRepository binaryContentRepository;

    @Override
    public void createBinaryContent(BinaryContentDTO.CreateBinaryContentRequest request) {

        BinaryContent binaryContent = BinaryContent.builder()
            .data(request.data()).fileType(request.fileType())
            .build();

        binaryContentRepository.save(binaryContent);

    }

    @Override
    public boolean existBinaryContentById(UUID id) {
        return binaryContentRepository.existById(id);
    }

    @Override
    public Optional<BinaryContentDTO.ReadBinaryContentResult> findBinaryContentById(UUID id) {

        BinaryContent binaryContent = binaryContentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No such binary content"));

        return Optional.ofNullable(BinaryContentDTO.ReadBinaryContentResult.builder()
                .id(binaryContent.getId())
                .data(binaryContent.getData())
                .createdAt(binaryContent.getCreatedAt())
                .fileType(binaryContent.getFileType())
                .build());
    }

    @Override
    public List<BinaryContentDTO.ReadBinaryContentResult> findAllBinaryContentByIdIn(List<UUID> uuidList) {
        return binaryContentRepository.findAllByIdIn(uuidList).stream()
                .map(binaryContent -> BinaryContentDTO.ReadBinaryContentResult.builder()
                        .id(binaryContent.getId())
                        .data(binaryContent.getData())
                        .createdAt(binaryContent.getCreatedAt())
                        .fileType(binaryContent.getFileType())
                        .build())
                .toList();
    }

    @Override
    public List<BinaryContentDTO.ReadBinaryContentResult> findAllBinaryContent() {
        return binaryContentRepository.findAll().stream()
                .map(binaryContent -> BinaryContentDTO.ReadBinaryContentResult.builder()
                        .id(binaryContent.getId())
                        .data(binaryContent.getData())
                        .createdAt(binaryContent.getCreatedAt())
                        .fileType(binaryContent.getFileType())
                        .build())
                .toList();
    }

    @Override
    public void deleteBinaryContentById(UUID id) {

        if (!binaryContentRepository.existById(id)) {
            throw new RuntimeException("No such binary content");
        }

        binaryContentRepository.deleteById(id);

    }
}
