package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileUserCrudServiceTest {

    UserService userService = new FileUserCrudService(Path.of("C:\\spring\\codeit-bootcamp-spring\\6-sprint-mission\\src\\main\\resources\\data\\users"));

    @Test
    void create() {

        //given
        User user = new User("test", "test", "test", "test");
        System.out.println(user.getId());

        //when
        userService.create(user);

        //then
        User user1 = userService.readById(user.getId()).orElse(null);
        assertNotNull(user1);
        System.out.println(user1.toString());
        assertEquals(user1.getId(), user.getId());
        userService.deleteById(user.getId());

    }

    @Test
    void existById() {

        //given
        User user = new User("test", "test", "test", "test");
        System.out.println(user.getId());

        //when
        userService.create(user);

        //then
        Assertions.assertTrue(userService.existById(user.getId()));
        userService.deleteById(user.getId());

    }

    @Test
    void readById() {

        //given
        User user = new User("test", "test", "test", "test");
        System.out.println(user.getId());

        //when
        userService.create(user);
        User user1 = userService.readById(user.getId()).orElse(null);

        //then
        assertEquals(user1.getId(), user.getId());
        userService.deleteById(user.getId());

    }

    @Test
    void readAll() {

        //given
        User user = new User("test", "test", "test", "test");
        System.out.println(user.getId());

        //when
        userService.create(user);
        List<User> users = userService.readAll();

        //then
        users.forEach(System.out::println);
        userService.deleteById(user.getId());

    }

    @Test
    void update() {

        //given
        User user = new User("test", "test", "test", "test");
        System.out.println(user.getId());
        userService.create(user);

        //when
        userService.update(user.getId(), "test2", "test2", "test2", "test2");
        User user1 = userService.readById(user.getId()).orElse(null);

        //then
        System.out.println(user1.toString());
        assertEquals(user1.getNickname(), "test2");
        userService.deleteById(user.getId());

    }

    @Test
    void deleteById() {

        //given
        User user = new User("test", "test", "test", "test");
        userService.create(user);

        //when
        userService.deleteById(user.getId());

        //then
        assertFalse(userService.readAll().contains(user));
        assertEquals(userService.readById(user.getId()).orElse(null), null);

    }
}