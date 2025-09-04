package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUserServiceTest {

    UserService userService = new FileUserService(Path.of("C:\\spring\\codeit-bootcamp-spring\\6-sprint-mission\\src\\main\\resources\\data\\users"));

    @Test
    void createUser() {

        //given
        UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test@test.com")
                .password("A39ffcsdg&fdsldsf")
                .description("test")
                .build();

        //when
        userService.createUser(userRequest);

        //then
        UserDTO.FindUserResult user1 = userService.findUserByEmail(userRequest.email()).orElse(null);
        assertNotNull(user1);
        System.out.println(user1.toString());
        assertEquals(user1.email(), userRequest.email());
        userService.deleteUserById(user1.id());

    }

    @Test
    void existUserById() {

        //given
        UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test@test.com")
                .password("A39ffcsdg&fdsldsf")
                .description("test")
                .build();

        //when
        userService.createUser(userRequest);

        //then
        Assertions.assertTrue(userService.existUserByEmail(userRequest.email()));
        userService.deleteUserById(userService.findUserByEmail(userRequest.email()).orElse(null).id());

    }

    @Test
    void findUserById() {

        //given
        UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test@test.com")
                .password("A39ffcsdg&fdsldsf")
                .description("test")
                .build();

        //when
        userService.createUser(userRequest);
        UserDTO.FindUserResult user1 = userService.findUserByEmail(userRequest.email()).orElse(null);

        //then
        assertEquals(user1.email(), userRequest.email());
        userService.deleteUserById(user1.id());

    }

    @Test
    void findAllUsers() {

        //given
        UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test@test.com")
                .password("A39ffcsdg&fdsldsf")
                .description("test")
                .build();

        //when
        userService.createUser(userRequest);
        List<UserDTO.FindUserResult> users = userService.findAllUsers();

        //then
        users.forEach(System.out::println);
        userService.deleteUserById(userService.findUserByEmail(userRequest.email()).orElse(null).id());

    }

    @Test
    void updateUser() {

        //given
        UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test@test.com")
                .password("A39ffcsdg&fdsldsf")
                .description("test")
                .build();
        userService.createUser(userRequest);

        //when
        UserDTO.FindUserResult user1 = userService.findUserByEmail(userRequest.email()).orElse(null);
        UserDTO.UpdateUserRequest updateUserRequest = UserDTO.UpdateUserRequest.builder()
                .id(user1.id())
                .nickname("test2")
                .email("test2@test.com")
                .currentPassword("A39ffcsdg&fdsldsf")
                .newPassword("A39ffcsdg&fdsldsf")
                .description("test2")
                .build();
        userService.updateUser(updateUserRequest);
        user1 = userService.findUserById(user1.id()).orElse(null);

        //then
        System.out.println(user1.toString());
        assertEquals(user1.nickname(), "test2");
        userService.deleteUserById(user1.id());

    }

    @Test
    void deleteUserById() {

        //given
        UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test@test.com")
                .password("A39ffcsdg&fdsldsf")
                .description("test")
                .build();
        userService.createUser(userRequest);

        //when
        UserDTO.FindUserResult user1 = userService.findUserByEmail(userRequest.email()).orElse(null);
        userService.deleteUserById(user1.id());

        //then
        assertFalse(userService.findAllUsers().contains(user1));
        assertEquals(null, userService.findUserById(user1.id()).orElse(null));

    }

    @Test
    void findUserByEmail() {

        //given
        UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test@test.com")
                .password("A39ffcsdg&fdsldsf")
                .description("test")
                .build();
        userService.createUser(userRequest);

        //when
        UserDTO.FindUserResult user1 = userService.findUserByEmail(userRequest.email()).get();

        //then
        assertEquals(user1.email(), userRequest.email());
        userService.deleteUserById(user1.id());

    }
}