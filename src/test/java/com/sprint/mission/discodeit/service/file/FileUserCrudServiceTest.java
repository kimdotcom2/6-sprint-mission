package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUserCrudServiceTest {

    UserService userService = new FileUserCrudService(Path.of("C:\\spring\\codeit-bootcamp-spring\\6-sprint-mission\\src\\main\\resources\\data\\users"));

    @Test
    void createUser() {

        //given
        User user = new User("test", "test", "test", "test");
        System.out.println(user.getId());

        //when
        userService.createUser(user);

        //then
        User user1 = userService.findUserById(user.getId()).orElse(null);
        assertNotNull(user1);
        System.out.println(user1.toString());
        assertEquals(user1.getId(), user.getId());
        userService.deleteUserById(user.getId());

    }

    @Test
    void existUserById() {

        //given
        User user = new User("test", "test", "test", "test");
        System.out.println(user.getId());

        //when
        userService.createUser(user);

        //then
        Assertions.assertTrue(userService.existUserById(user.getId()));
        userService.deleteUserById(user.getId());

    }

    @Test
    void findUserById() {

        //given
        User user = new User("test", "test", "test", "test");
        System.out.println(user.getId());

        //when
        userService.createUser(user);
        User user1 = userService.findUserById(user.getId()).orElse(null);

        //then
        assertEquals(user1.getId(), user.getId());
        userService.deleteUserById(user.getId());

    }

    @Test
    void findAllUsers() {

        //given
        User user = new User("test", "test", "test", "test");
        System.out.println(user.getId());

        //when
        userService.createUser(user);
        List<User> users = userService.findAllUsers();

        //then
        users.forEach(System.out::println);
        userService.deleteUserById(user.getId());

    }

    @Test
    void updateUser() {

        //given
        User user = new User("test", "test", "test", "test");
        System.out.println(user.getId());
        userService.createUser(user);

        //when
        userService.updateUser(user.getId(), "test2", "test2", "test2", "test2");
        User user1 = userService.findUserById(user.getId()).orElse(null);

        //then
        System.out.println(user1.toString());
        assertEquals(user1.getNickname(), "test2");
        userService.deleteUserById(user.getId());

    }

    @Test
    void deleteUserById() {

        //given
        User user = new User("test", "test", "test", "test");
        userService.createUser(user);

        //when
        userService.deleteUserById(user.getId());

        //then
        assertFalse(userService.findAllUsers().contains(user));
        assertEquals(userService.findUserById(user.getId()).orElse(null), null);

    }

    @Test
    void findUserByEmail() {

        //given
        User user = new User("test", "test", "test", "test");
        userService.createUser(user);

        //when
        User user1 = userService.findUserByEmail(user.getEmail()).orElse(null);

        //then
        assertEquals(user1.getEmail(), user.getEmail());
        userService.deleteUserById(user.getId());

    }
}