package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileMessageCrudServiceTest {

    String fileDirectory = "C:\\spring\\codeit-bootcamp-spring\\6-sprint-mission\\src\\main\\resources\\data\\";
    UserService fileUserService = new FileUserCrudService(Path.of(fileDirectory + "users"));
    ChannelService channelService = new FileChanelCrudService(Path.of(fileDirectory + "channels"));
    MessageService messageService = new FileMessageCrudService(Path.of(fileDirectory + "messages"), fileUserService, channelService);

    @Test
    void create() {

        //given
        User user = new User("test", "test", "test", "test");
        Channel channel = new Channel("test", "test", true);
        Message message = new Message(user.getId(), channel.getId(), "test", false, null);
        fileUserService.create(user);
        channelService.create(channel);
        messageService.create(message);

        //when
        Message message1 = messageService.readById(message.getId()).orElse(null);
        System.out.println(message1.toString());

        //then
        assertNotNull(message1);
        assertEquals(message1.getId(), message.getId());
        messageService.deleteById(message.getId());
        fileUserService.deleteById(user.getId());
        channelService.deleteById(channel.getId());

    }

    @Test
    void existById() {

        //given
        User user = new User("test", "test", "test", "test");
        Channel channel = new Channel("test", "test", true);
        Message message = new Message(user.getId(), channel.getId(), "test", false, null);
        fileUserService.create(user);
        channelService.create(channel);
        messageService.create(message);

        //when
        boolean exist = messageService.existById(message.getId());

        //then
        assertTrue(exist);
        messageService.deleteById(message.getId());
        fileUserService.deleteById(user.getId());
        channelService.deleteById(channel.getId());

    }

    @Test
    void readById() {

        //given
        User user = new User("test", "test", "test", "test");
        Channel channel = new Channel("test", "test", true);
        Message message = new Message(user.getId(), channel.getId(), "test", false, null);
        fileUserService.create(user);
        channelService.create(channel);
        messageService.create(message);

        //when
        Message message1 = messageService.readById(message.getId()).orElse(null);

        //then
        System.out.println(message1.toString());
        assertNotNull(message1);
        assertEquals(message1.getId(), message.getId());
        messageService.deleteById(message.getId());
        fileUserService.deleteById(user.getId());
        channelService.deleteById(channel.getId());

    }

    @Test
    void readChildrenById() {

        //given
        User user = new User("test", "test", "test", "test");
        Channel channel = new Channel("test", "test", true);
        Message message = new Message(user.getId(), channel.getId(), "test", false, null);
        Message message1 = new Message(user.getId(), channel.getId(), "test1", true, message.getId());
        Message message2 = new Message(user.getId(), channel.getId(), "test2", true, message.getId());
        fileUserService.create(user);
        channelService.create(channel);
        messageService.create(message);
        messageService.create(message1);
        messageService.create(message2);

        //when
        List<Message> messageList = messageService.readChildrenById(message.getId());

        //then
        messageList.forEach(m -> System.out.println(m.toString()));
        assertEquals(messageList.get(0).getId(), message1.getId());
        messageService.deleteById(message.getId());
        messageService.deleteById(message1.getId());
        messageService.deleteById(message2.getId());
        fileUserService.deleteById(user.getId());
        channelService.deleteById(channel.getId());


    }

    @Test
    void readAll() {

        //given
        User user = new User("test", "test", "test", "test");
        Channel channel = new Channel("test", "test", true);
        Message message = new Message(user.getId(), channel.getId(), "test", false, null);
        Message message1 = new Message(user.getId(), channel.getId(), "test1", true, message.getId());
        Message message2 = new Message(user.getId(), channel.getId(), "test2", true, message.getId());
        fileUserService.create(user);
        channelService.create(channel);
        messageService.create(message);
        messageService.create(message1);
        messageService.create(message2);

        //when
        List<Message> messageList = messageService.readAll();

        //then
        messageList.forEach(m -> System.out.println(m.toString()));
        assertEquals(messageList.get(0).getId(), message.getId());
        messageService.deleteById(message.getId());
        messageService.deleteById(message1.getId());
        messageService.deleteById(message2.getId());
        fileUserService.deleteById(user.getId());
        channelService.deleteById(channel.getId());

    }

    @Test
    void update() {

        //given
        User user = new User("test", "test", "test", "test");
        Channel channel = new Channel("test", "test", true);
        Message message = new Message(user.getId(), channel.getId(), "test", false, null);
        fileUserService.create(user);
        channelService.create(channel);
        messageService.create(message);

        //when
        Message message1 = messageService.readById(message.getId()).orElse(null);
        messageService.update(message1.getId(), "test2", false, null);

        //then
        System.out.println(messageService.readById(message.getId()).orElse(null).toString());
        assertTrue(messageService.existById(message1.getId()));
        assertEquals(messageService.readById(message.getId()).orElse(null).getId(), message1.getId());
        assertEquals(messageService.readById(message.getId()).orElse(null).getContent(), "test2");
        messageService.deleteById(message1.getId());
        fileUserService.deleteById(user.getId());
        channelService.deleteById(channel.getId());

    }

    @Test
    void deleteById() {

        //given
        User user = new User("test", "test", "test", "test");
        Channel channel = new Channel("test", "test", true);
        Message message = new Message(user.getId(), channel.getId(), "test", false, null);
        fileUserService.create(user);
        channelService.create(channel);
        messageService.create(message);

        //when
        messageService.deleteById(message.getId());

        //then
        assertFalse(messageService.existById(message.getId()));
        fileUserService.deleteById(user.getId());
        channelService.deleteById(channel.getId());

    }
}