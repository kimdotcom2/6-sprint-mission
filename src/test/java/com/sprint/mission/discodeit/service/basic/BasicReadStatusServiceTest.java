package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasicReadStatusServiceTest {

    @Mock
    private ReadStatusRepository readStatusRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ChannelRepository channelRepository;

    @InjectMocks
    private BasicReadStatusService basicReadStatusService;


    private ReadStatusDTO.CreateReadStatusCommand createReadStatusRequest(UUID userId, UUID channelId) {
        return ReadStatusDTO.CreateReadStatusCommand.builder()
                .userId(userId)
                .channelId(channelId)
                .build();
    }

    private ReadStatus sample(UUID userId, UUID channelId) {
        return new ReadStatus.Builder()
                .userId(userId)
                .channelId(channelId)
                .build();
    }

    /*@Test
    void createReadStatus_success() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(true);
        when(channelRepository.existById(channelId)).thenReturn(true);
        ReadStatusDTO.CreateReadStatusCommand request = createReadStatusRequest(userId, channelId);

        //when
        basicReadStatusService.createReadStatus(request);

        //then
        verify(readStatusRepository, times(1)).save(any(ReadStatus.class));

    }

    @Test
    void createReadStatus_noUser_throws() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(false);
        ReadStatusDTO.CreateReadStatusCommand request = createReadStatusRequest(userId, channelId);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.createReadStatus(request));

    }

    @Test
    void createReadStatus_noChannel_throws() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(true);
        when(channelRepository.existById(channelId)).thenReturn(false);
        ReadStatusDTO.CreateReadStatusCommand request = createReadStatusRequest(userId, channelId);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.createReadStatus(request));

    }

    @Test
    void createReadStatus_AlreadyExists_throws() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(true);
        when(channelRepository.existById(channelId)).thenReturn(true);
        ReadStatusDTO.CreateReadStatusCommand request = createReadStatusRequest(userId, channelId);
        when(readStatusRepository.existByUserIdAndChannelId(userId, channelId)).thenReturn(true);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.createReadStatus(request));

    }

    @Test
    void existReadStatusById_delegates() {

        //given
        UUID id = UUID.randomUUID();
        when(readStatusRepository.existById(id)).thenReturn(true);

        //then
        Assertions.assertTrue(basicReadStatusService.existReadStatusById(id));

    }

    @Test
    void existReadStatusByUserIdAndChannelId_delegates() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(readStatusRepository.existByUserIdAndChannelId(userId, channelId)).thenReturn(true);

        //then
        Assertions.assertTrue(basicReadStatusService.existReadStatusByUserIdAndChannelId(userId, channelId));

    }

    @Test
    void findReadStatusById_success() {

        //given
        ReadStatus readStatus = sample(UUID.randomUUID(), UUID.randomUUID());
        when(readStatusRepository.findById(readStatus.getId())).thenReturn(Optional.of(readStatus));

        //when
        ReadStatusDTO.FindReadStatusResult result = basicReadStatusService.findReadStatusById(readStatus.getId()).orElse(null);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(readStatus.getId(), result.id());
        Assertions.assertEquals(readStatus.getUserId(), result.userId());
        Assertions.assertEquals(readStatus.getChannelId(), result.channelId());

    }

    @Test
    void findReadStatusById_notFound_throws() {

        //given
        UUID id = UUID.randomUUID();
        when(readStatusRepository.findById(id)).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.findReadStatusById(id));

    }

    @Test
    void findReadStatusByUserIdAndChannelId_success() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        ReadStatus readStatus = sample(userId, channelId);
        when(userRepository.existById(userId)).thenReturn(true);
        when(channelRepository.existById(channelId)).thenReturn(true);
        when(readStatusRepository.findByUserIdAndChannelId(userId, channelId)).thenReturn(Optional.of(readStatus));

        //when
        ReadStatusDTO.FindReadStatusResult result = basicReadStatusService.findReadStatusByUserIdAndChannelId(userId, channelId).orElse(null);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(readStatus.getId(), result.id());

    }

    @Test
    void findReadStatusByUserIdAndChannelId_noUser_throws() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(false);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.findReadStatusByUserIdAndChannelId(userId, channelId));

    }

    @Test
    void findReadStatusByUserIdAndChannelId_noChannel_throws() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(true);
        when(channelRepository.existById(channelId)).thenReturn(false);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.findReadStatusByUserIdAndChannelId(userId, channelId));

    }

    @Test
    void findAllReadStatusByUserId_success() {

        //given
        UUID userId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(true);
        ReadStatus readStatus = sample(userId, UUID.randomUUID());
        when(readStatusRepository.findByUserId(userId)).thenReturn(List.of(readStatus));

        //when
        List<ReadStatusDTO.FindReadStatusResult> results = basicReadStatusService.findAllReadStatusByUserId(userId);

        //then
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(readStatus.getId(), results.get(0).id());

    }

    @Test
    void findAllReadStatusByUserId_noUser_throws() {

        //given
        UUID userId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(false);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.findAllReadStatusByUserId(userId));

    }

    @Test
    void findAllReadStatusByChannelId_success() {

        //given
        UUID channelId = UUID.randomUUID();
        when(channelRepository.existById(channelId)).thenReturn(true);
        ReadStatus rs = sample(UUID.randomUUID(), channelId);
        when(readStatusRepository.findByChannelId(channelId)).thenReturn(List.of(rs));

        //when
        List<ReadStatusDTO.FindReadStatusResult> results = basicReadStatusService.findAllReadStatusByChannelId(channelId);

        //then
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(rs.getId(), results.get(0).id());

    }

    @Test
    void findAllReadStatusByChannelId_noChannel_throws() {

        //given
        UUID channelId = UUID.randomUUID();
        when(channelRepository.existById(channelId)).thenReturn(false);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.findAllReadStatusByChannelId(channelId));

    }

    @Test
    void findAllReadStatus_success() {

        //given
        ReadStatus rs = sample(UUID.randomUUID(), UUID.randomUUID());
        when(readStatusRepository.findAll()).thenReturn(List.of(rs));

        //when
        List<ReadStatusDTO.FindReadStatusResult> results = basicReadStatusService.findAllReadStatus();

        //then
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(rs.getId(), results.get(0).id());

    }

    @Test
    void updateReadStatus_success_updatesTimestampAndSaves() {

        //given
        ReadStatus existing = sample(UUID.randomUUID(), UUID.randomUUID());
        long before = existing.getLastReadTimestamp();
        when(readStatusRepository.findById(existing.getId())).thenReturn(Optional.of(existing));

        //when
        ReadStatusDTO.UpdateReadStatusCommand request = ReadStatusDTO.UpdateReadStatusCommand.builder()
                .id(existing.getId())
                .build();
        basicReadStatusService.updateReadStatus(request);

        //then
        ArgumentCaptor<ReadStatus> captor = ArgumentCaptor.forClass(ReadStatus.class);
        verify(readStatusRepository, times(1)).save(captor.capture());
        ReadStatus saved = captor.getValue();
        Assertions.assertTrue(saved.getLastReadTimestamp() >= before);

    }

    @Test
    void updateReadStatus_notFound_throws() {

        //given
        UUID id = UUID.randomUUID();
        when(readStatusRepository.findById(id)).thenReturn(Optional.empty());

        //when
        ReadStatusDTO.UpdateReadStatusCommand request = ReadStatusDTO.UpdateReadStatusCommand.builder()
                .id(id)
                .build();

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.updateReadStatus(request));

    }

    @Test
    void deleteReadStatusById_success() {

        //given
        UUID id = UUID.randomUUID();
        when(readStatusRepository.existById(id)).thenReturn(true);

        //when
        basicReadStatusService.deleteReadStatusById(id);

        //then
        verify(readStatusRepository, times(1)).deleteById(id);

    }

    @Test
    void deleteReadStatusById_notFound_throws() {

        //given
        UUID id = UUID.randomUUID();
        when(readStatusRepository.existById(id)).thenReturn(false);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.deleteReadStatusById(id));

    }

    @Test
    void deleteReadStatusByUserIdAndChannelId_success() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(true);
        when(channelRepository.existById(channelId)).thenReturn(true);

        //when
        basicReadStatusService.deleteReadStatusByUserIdAndChannelId(userId, channelId);

        //then
        verify(readStatusRepository, times(1)).deleteByUserIdAndChannelId(userId, channelId);

    }

    @Test
    void deleteReadStatusByUserIdAndChannelId_checksAndThrows() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(false);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.deleteReadStatusByUserIdAndChannelId(userId, channelId));

        //given
        when(userRepository.existById(userId)).thenReturn(true);
        when(channelRepository.existById(channelId)).thenReturn(false);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.deleteReadStatusByUserIdAndChannelId(userId, channelId));

    }

    @Test
    void deleteAllReadStatusByIdInByUserId_success() {

        //given
        UUID userId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(true);

        //when
        basicReadStatusService.deleteAllReadStatusByUserId(userId);

        //then
        verify(readStatusRepository, times(1)).deleteByUserId(userId);

    }

    @Test
    void deleteAllReadStatusByIdInByUserId_noUser_throws() {

        //given
        UUID userId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(false);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.deleteAllReadStatusByUserId(userId));

    }

    @Test
    void deleteAllReadStatusByIdInByChannelId_success() {

        //given
        UUID channelId = UUID.randomUUID();
        when(channelRepository.existById(channelId)).thenReturn(true);

        //when
        basicReadStatusService.deleteAllReadStatusByChannelId(channelId);

        //then
        verify(readStatusRepository, times(1)).deleteByChannelId(channelId);

    }

    @Test
    void deleteAllReadStatusByIdInByChannelId_noChannel_throws() {

        //given
        UUID channelId = UUID.randomUUID();
        when(channelRepository.existById(channelId)).thenReturn(false);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.deleteAllReadStatusByChannelId(channelId));

    }

    @Test
    void deleteAllReadStatus_success_afterValidatingAllIdsExistByIdIn() {

        //given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        when(readStatusRepository.existById(id1)).thenReturn(true);
        when(readStatusRepository.existById(id2)).thenReturn(true);
        List<UUID> uuidList = List.of(id1, id2);

        //when
        basicReadStatusService.deleteAllReadStatusByIdIn(uuidList);

        //then
        verify(readStatusRepository, times(1)).deleteAllByIdIn(uuidList);

    }

    @Test
    void deleteAllReadStatus_ByIdIn_containsMissingId_throws() {

        //given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        when(readStatusRepository.existById(id1)).thenReturn(true);
        when(readStatusRepository.existById(id2)).thenReturn(false);
        List<UUID> uuidList = List.of(id1, id2);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicReadStatusService.deleteAllReadStatusByIdIn(uuidList));

    }*/
}
