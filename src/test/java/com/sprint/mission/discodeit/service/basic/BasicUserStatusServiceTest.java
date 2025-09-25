package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatusDTO;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
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
class BasicUserStatusServiceTest {

    @Mock
    private UserStatusRepository userStatusRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BasicUserStatusService service;

    /*@Test
    void createUserStatus_behaviour() {

        //given
        UUID userId = UUID.randomUUID();
        UserStatusDTO.CreateUserStatusCommand request = UserStatusDTO.CreateUserStatusCommand.builder()
                .userId(userId)
                .build();
        when(userRepository.existById(userId)).thenReturn(true);
        when(userStatusRepository.existByUserId(userId)).thenReturn(false);

        //when
        service.createUserStatus(request);

        //then
        ArgumentCaptor<UserStatus> captor = ArgumentCaptor.forClass(UserStatus.class);
        verify(userStatusRepository).save(captor.capture());
        UserStatus saved = captor.getValue();
        assertThat(saved.getUserId()).isEqualTo(userId);

        //given
        UUID missingUserId = UUID.randomUUID();
        UserStatusDTO.CreateUserStatusCommand requestMissingUser = UserStatusDTO.CreateUserStatusCommand.builder()
                .userId(missingUserId)
                .build();
        when(userRepository.existById(missingUserId)).thenReturn(false);

        //then
        assertThatThrownBy(() -> service.createUserStatus(requestMissingUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No such user.");

        //given
        UUID duplicatedUserId = UUID.randomUUID();
        UserStatusDTO.CreateUserStatusCommand duplicatedRequest = UserStatusDTO.CreateUserStatusCommand.builder()
                .userId(duplicatedUserId)
                .build();
        when(userRepository.existById(duplicatedUserId)).thenReturn(true);
        when(userStatusRepository.existByUserId(duplicatedUserId)).thenReturn(true);

        //then
        assertThatThrownBy(() -> service.createUserStatus(duplicatedRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User status already exists.");

    }

    @Test
    void existUserStatusById_and_ByUserId_delegateToRepository() {

        //given
        UUID id = UUID.randomUUID();
        when(userStatusRepository.existById(id)).thenReturn(true);
        when(userStatusRepository.existByUserId(id)).thenReturn(false);

        //when
        boolean byId = service.existUserStatusById(id);
        boolean byUserId = service.existUserStatusByUserId(id);

        //then
        assertThat(byId).isTrue();
        assertThat(byUserId).isFalse();
        verify(userStatusRepository).existById(id);
        verify(userStatusRepository).existByUserId(id);

    }

    @Test
    void findUserStatusById_mapsToDtoOrThrows() {

        //given
        UUID id = UUID.randomUUID();
        UserStatus entity = new UserStatus(UUID.randomUUID());
        when(userStatusRepository.findById(id)).thenReturn(Optional.of(entity));

        //when
        Optional<UserStatusDTO.FindUserStatusResult> found = service.findUserStatusById(id);

        //then
        assertThat(found).isPresent();
        assertThat(found.get().id()).isEqualTo(entity.getId());
        assertThat(found.get().userId()).isEqualTo(entity.getUserId());

        //given
        UUID missing = UUID.randomUUID();
        when(userStatusRepository.findById(missing)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> service.findUserStatusById(missing))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No such user status.");

    }

    @Test
    void findUserStatusByUserId_validatesUserThenMapsOrThrows() {

        //given
        UUID userId = UUID.randomUUID();
        UserStatus entity = new UserStatus(userId);
        when(userRepository.existById(userId)).thenReturn(true);
        when(userStatusRepository.findByUserId(userId)).thenReturn(Optional.of(entity));

        //when
        Optional<UserStatusDTO.FindUserStatusResult> found = service.findUserStatusByUserId(userId);

        //then
        assertThat(found).isPresent();
        assertThat(found.get().userId()).isEqualTo(userId);

        //given
        UUID noUser = UUID.randomUUID();
        when(userRepository.existById(noUser)).thenReturn(false);

        //then
        assertThatThrownBy(() -> service.findUserStatusByUserId(noUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No such user.");

        //given
        UUID noStatusUser = UUID.randomUUID();
        when(userRepository.existById(noStatusUser)).thenReturn(true);
        when(userStatusRepository.findByUserId(noStatusUser)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> service.findUserStatusByUserId(noStatusUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No such user status.");

    }

    @Test
    void findAllUserStatus_mapsList() {

        //given
        when(userStatusRepository.findAll()).thenReturn(List.of(new UserStatus(UUID.randomUUID())));

        //when
        List<UserStatusDTO.FindUserStatusResult> list = service.findAllUserStatus();

        //then
        assertThat(list).hasSize(1);
        assertThat(list.get(0).id()).isNotNull();

    }

    @Test
    void updateUserStatus_updatesTimestampAndSaves() {

        //given
        UUID id = UUID.randomUUID();
        UserStatus userStatus = spy(new UserStatus(UUID.randomUUID()));
        when(userStatusRepository.findById(id)).thenReturn(Optional.of(userStatus));

        //when
        service.updateUserStatus(UserStatusDTO.UpdateUserStatusCommand.builder().id(id).build());

        //then
        verify(userStatus).updateLastActiveTimestamp();
        verify(userStatusRepository).save(userStatus);

    }

    @Test
    void deleteUserStatusById_checksExistenceThenDeletes() {

        //given
        UUID id = UUID.randomUUID();
        when(userStatusRepository.existById(id)).thenReturn(true);

        //when
        service.deleteUserStatusById(id);

        //then
        verify(userStatusRepository).deleteById(id);

        //given
        UUID missing = UUID.randomUUID();
        when(userStatusRepository.existById(missing)).thenReturn(false);

        //then
        assertThatThrownBy(() -> service.deleteUserStatusById(missing))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No such user status.");

    }

    @Test
    void deleteUserStatusByUserId_usesExistByIdThenDeleteByUserId() {

        //given
        UUID userId = UUID.randomUUID();
        when(userStatusRepository.existById(userId)).thenReturn(true);

        //when
        service.deleteUserStatusByUserId(userId);

        //then
        verify(userStatusRepository).deleteByUserId(userId);

        //given
        UUID missing = UUID.randomUUID();
        when(userStatusRepository.existById(missing)).thenReturn(false);

        //then
        assertThatThrownBy(() -> service.deleteUserStatusByUserId(missing))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No such user status.");

    }

    @Test
    void deleteAllUserStatusByIdIn_validatesAllThenDeletes() {

        //given
        UUID uuid = UUID.randomUUID();
        UUID uuid1 = UUID.randomUUID();
        when(userStatusRepository.existById(uuid)).thenReturn(true);
        when(userStatusRepository.existById(uuid1)).thenReturn(true);

        //when
        service.deleteAllUserStatusByIdIn(List.of(uuid, uuid1));

        //then
        verify(userStatusRepository).deleteAllByIdIn(List.of(uuid, uuid1));

        //given
        UUID c = UUID.randomUUID();
        when(userStatusRepository.existById(c)).thenReturn(false);

        //then
        assertThatThrownBy(() -> service.deleteAllUserStatusByIdIn(List.of(c)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No such user status.");

    }*/
}
