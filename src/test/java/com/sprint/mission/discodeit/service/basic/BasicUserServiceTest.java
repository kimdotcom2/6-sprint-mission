package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.enums.FileType;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.utils.SecurityUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class BasicUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserStatusRepository userStatusRepository;
    @Mock
    private BinaryContentRepository binaryContentRepository;


    @InjectMocks
    private BasicUserService basicUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    User user = new User.Builder()
            .nickname("test")
            .email("test@test.com")
            .password("A39ffcsdg&fdsldsf")
            .description("test")
            .build();
    UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
            .nickname("test")
            .email("test@test.com")
            .password("A39ffcsdg&fdsldsf")
            .description("test")
            .profileImage(String.valueOf(1).getBytes(StandardCharsets.UTF_8))
            .fileType(FileType.IMAGE)
            .build();

    UserDTO.UpdateUserRequest updateUserRequest = UserDTO.UpdateUserRequest.builder()
            .id(user.getId())
            .nickname("test")
            .email("test@test.com")
            .currentPassword(user.getPassword())
            .newPassword("A39ffcsdg&fdsldsf2")
            .description("test2")
            .profileImage(String.valueOf(2).getBytes(StandardCharsets.UTF_8))
            .fileType(FileType.IMAGE)
            .build();

    @Test
    void createUser() {

        // Given

        // When
        basicUserService.createUser(userRequest);

        // Then
        Assertions.assertEquals(userRequest.nickname(), user.getNickname());
        Assertions.assertEquals(userRequest.email(), user.getEmail());
        Assertions.assertEquals(userRequest.description(), user.getDescription());

    }

    @Test
    void existUserById() {

        // Given
        when(userRepository.existById(user.getId())).thenReturn(true);

        // When
        boolean result = basicUserService.existUserById(user.getId());

        // Then
        Assertions.assertTrue(result);

    }

    @Test
    void existUserByEmail() {

        // Given
        when(userRepository.existByEmail(user.getEmail())).thenReturn(true);

        // When
        boolean result = basicUserService.existUserByEmail(user.getEmail());

        // Then
        Assertions.assertTrue(result);

    }

    @Test
    void findUserById() {

        // Given
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userStatusRepository.findById(user.getId())).thenReturn(Optional.of(new UserStatus(user.getId())));

        // When
        UserDTO.FindUserResult result = basicUserService.findUserById(user.getId()).orElse(null);

        // Then
        Assertions.assertEquals(user.getId(), result.id());

    }

    @Test
    void findUserByEmail() {

        // Given
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userStatusRepository.findById(user.getId())).thenReturn(Optional.of(new UserStatus(user.getId())));

        // When
        UserDTO.FindUserResult result = basicUserService.findUserByEmail(user.getEmail()).orElse(null);

        // Then
        Assertions.assertEquals(user.getId(), result.id());

    }

    @Test
    void findAllUsers() {

        // Given
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userStatusRepository.findByUserId(user.getId())).thenReturn(Optional.of(new UserStatus(user.getId())));

        // When
        List<UserDTO.FindUserResult> result = basicUserService.findAllUsers();

        // Then
        Assertions.assertEquals(user.getId(), result.get(0).id());

    }

    @Test
    void updateUser() {

        // Given
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        user.updatePassword(new SecurityUtil().hashPassword(user.getPassword()));
        when(userRepository.existByNickname(userRequest.nickname())).thenReturn(false);
        when(userRepository.existByEmail(userRequest.email())).thenReturn(false);
        when(userRepository.existById(user.getId())).thenReturn(true);

        // When
        basicUserService.updateUser(updateUserRequest);

        // Then

    }

    @Test
    void deleteUserById() {

        // Given
        when(userRepository.existById(user.getId())).thenReturn(true);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // When
        basicUserService.deleteUserById(user.getId());

        // Then

    }
}
