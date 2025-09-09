package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.enums.FileType;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasicBinaryContentServiceTest {

    @Mock
    private BinaryContentRepository binaryContentRepository;

    @InjectMocks
    private BasicBinaryContentService service;

    private byte[] sampleData;

    @BeforeEach
    void setUp() {
        sampleData = new byte[]{1, 2, 3};
    }

    @Test
    void createBinaryContent_behaviour() {

        //given
        BinaryContentDTO.CreateBinaryContentRequest request = BinaryContentDTO.CreateBinaryContentRequest.builder()
                .data(sampleData)
                .fileType(FileType.IMAGE)
                .build();

        //when
        service.createBinaryContent(request);

        //then
        ArgumentCaptor<BinaryContent> captor = ArgumentCaptor.forClass(BinaryContent.class);
        verify(binaryContentRepository, times(1)).save(captor.capture());
        BinaryContent binaryContent = captor.getValue();
        assertThat(binaryContent.getData()).isEqualTo(sampleData);
        assertThat(binaryContent.getFileType()).isEqualTo(FileType.IMAGE);

        //given
        BinaryContentDTO.CreateBinaryContentRequest nullDataRequest = BinaryContentDTO.CreateBinaryContentRequest.builder()
                .data(null)
                .fileType(FileType.DOCUMENT)
                .build();
        BinaryContentDTO.CreateBinaryContentRequest nullTypeRequest = BinaryContentDTO.CreateBinaryContentRequest.builder()
                .data(sampleData)
                .fileType(null)
                .build();

        //then
        assertThatThrownBy(() -> service.createBinaryContent(nullDataRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Data must not be null");
        assertThatThrownBy(() -> service.createBinaryContent(nullTypeRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("FileType must not be null");

    }

    @Test
    void existBinaryContentById_delegatesToRepository() {

        //given
        UUID id = UUID.randomUUID();
        when(binaryContentRepository.existById(id)).thenReturn(true);

        //when
        boolean result = service.existBinaryContentById(id);

        //then
        assertThat(result).isTrue();
        verify(binaryContentRepository).existById(id);

    }

    @Test
    void findBinaryContentById_mapsEntityToDto() {

        //given
        UUID id = UUID.randomUUID();
        BinaryContent entity = BinaryContent.builder()
                .data(sampleData)
                .fileType(FileType.IMAGE)
                .build();
        when(binaryContentRepository.findById(id)).thenReturn(Optional.of(entity));

        //when
        Optional<BinaryContentDTO.ReadBinaryContentResult> result = service.findBinaryContentById(id);

        //then
        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(entity.getId());
        assertThat(result.get().data()).isEqualTo(sampleData);
        assertThat(result.get().fileType()).isEqualTo(FileType.IMAGE);

    }

    @Test
    void findBinaryContentById_throwsIfMissing() {

        //given
        UUID id = UUID.randomUUID();
        when(binaryContentRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> service.findBinaryContentById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No such binary content");

    }

    @Test
    void findAllBinaryContentByIdIn_mapsList() {

        //given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        BinaryContent e1 = BinaryContent.builder().data(new byte[]{1}).fileType(FileType.IMAGE).build();
        BinaryContent e2 = BinaryContent.builder().data(new byte[]{2}).fileType(FileType.DOCUMENT).build();
        when(binaryContentRepository.findAllByIdIn(List.of(id1, id2))).thenReturn(List.of(e1, e2));

        //when
        List<BinaryContentDTO.ReadBinaryContentResult> list = service.findAllBinaryContentByIdIn(List.of(id1, id2));

        //then
        assertThat(list).hasSize(2);
        assertThat(list.get(0).data()).containsExactly((byte)1);
        assertThat(list.get(1).fileType()).isEqualTo(FileType.DOCUMENT);

    }

    @Test
    void findAllBinaryContent_mapsList() {

        //given
        BinaryContent e1 = BinaryContent.builder().data(new byte[]{1}).fileType(FileType.IMAGE).build();
        when(binaryContentRepository.findAll()).thenReturn(List.of(e1));

        //when
        List<BinaryContentDTO.ReadBinaryContentResult> list = service.findAllBinaryContent();

        //then
        assertThat(list).hasSize(1);
        assertThat(list.get(0).data()).containsExactly((byte)1);

    }

    @Test
    void deleteBinaryContentById_checksExistenceThenDeletes() {

        //given
        UUID id = UUID.randomUUID();
        when(binaryContentRepository.existById(id)).thenReturn(true);

        //when
        service.deleteBinaryContentById(id);

        //then
        verify(binaryContentRepository).deleteById(id);

        //given
        UUID missing = UUID.randomUUID();
        when(binaryContentRepository.existById(missing)).thenReturn(false);

        //then
        assertThatThrownBy(() -> service.deleteBinaryContentById(missing))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No such binary content");

    }
}
