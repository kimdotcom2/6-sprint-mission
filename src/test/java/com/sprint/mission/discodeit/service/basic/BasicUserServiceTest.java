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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserStatusRepository userStatusRepository;
    @Mock
    private BinaryContentRepository binaryContentRepository;


    @InjectMocks
    private BasicUserService basicUserService;


    User user = new User.Builder()
            .nickname("test")
            .email("test@test.com")
            .password("A39ffcsdg&fdsldsf")
            .description("test")
            .build();
    UserDTO.CreateUserCommand userRequest = UserDTO.CreateUserCommand.builder()
            .nickname("test")
            .email("test@test.com")
            .password("A39ffcsdg&fdsldsf")
            .description("test")
            .profileImage(String.valueOf(1).getBytes(StandardCharsets.UTF_8))
            .fileType(FileType.IMAGE)
            .build();

    UserDTO.UpdateUserCommand updateUserCommand = UserDTO.UpdateUserCommand.builder()
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

        //when
        basicUserService.createUser(userRequest);

        //then
        Assertions.assertEquals(userRequest.nickname(), user.getNickname());
        Assertions.assertEquals(userRequest.email(), user.getEmail());
        Assertions.assertEquals(userRequest.description(), user.getDescription());

    }

    @Test
    void existUserById() {

        //given
        when(userRepository.existById(user.getId())).thenReturn(true);

        //when
        boolean result = basicUserService.existUserById(user.getId());

        //then
        Assertions.assertTrue(result);

    }

    @Test
    void existUserByEmail() {

        //given
        when(userRepository.existByEmail(user.getEmail())).thenReturn(true);

        //when
        boolean result = basicUserService.existUserByEmail(user.getEmail());

        //then
        Assertions.assertTrue(result);

    }

    @Test
    void findUserById() {

        //given
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userStatusRepository.findByUserId(user.getId())).thenReturn(Optional.of(new UserStatus(user.getId())));

        //when
        UserDTO.FindUserResult result = basicUserService.findUserById(user.getId()).orElse(null);

        //then
        Assertions.assertEquals(user.getId(), result.id());

    }

    @Test
    void findUserByEmail() {

        //given
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userStatusRepository.findByUserId(user.getId())).thenReturn(Optional.of(new UserStatus(user.getId())));

        //when
        UserDTO.FindUserResult result = basicUserService.findUserByEmail(user.getEmail()).orElse(null);

        //then
        Assertions.assertEquals(user.getId(), result.id());

    }

    @Test
    void findAllUsers() {

        //given
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userStatusRepository.findByUserId(user.getId())).thenReturn(Optional.of(new UserStatus(user.getId())));

        //when
        List<UserDTO.FindUserResult> result = basicUserService.findAllUsers();

        //then
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

        //when
        basicUserService.updateUser(updateUserCommand);

    }

    @Test
    void deleteUserById() {

        //given
        when(userRepository.existById(user.getId())).thenReturn(true);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        //when
        basicUserService.deleteUserById(user.getId());

        //then

    }
}
