package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileMessageServiceTest {

    String fileDirectory = "C:\\spring\\codeit-bootcamp-spring\\6-sprint-mission\\src\\main\\resources\\data\\";
    UserService fileUserService = new FileUserService(Path.of(fileDirectory + "users"));
    ChannelService channelService = new FileChannelService(Path.of(fileDirectory + "channels"));
    MessageService messageService = new FileMessageService(Path.of(fileDirectory + "messages"), fileUserService, channelService);

    /*@Test
    void createMessage() {

        //given
        UserDTO.CreateUserRequest user = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test")
                .password("test")
                .description("test")
                .build();
        fileUserService.createUser(user);
        *//*Channel channel = new Channel("test", ChannelType.TEXT, true, false);
        Message message = new Message(fileUserService.findUserByEmail(user.email())
                .orElse(null).id(), channel.getId(), "test", false, null);*//*
        channelService.createChannel(channel);
        messageService.createMessage(message);

        //when
        Message message1 = messageService.findMessageById(message.getId()).orElse(null);
        System.out.println(message1.toString());

        //then
        assertNotNull(message1);
        assertEquals(message1.getId(), message.getId());
        messageService.deleteMessageById(message.getId());
        fileUserService.deleteUserById(fileUserService.findUserByEmail(user.email())
                .orElse(null).id());
        channelService.deleteChannelById(channel.getId());

    }

    @Test
    void existMessageById() {

        //given
        UserDTO.CreateUserRequest user = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test")
                .password("test")
                .description("test")
                .build();
        fileUserService.createUser(user);
        Channel channel = new Channel("test", ChannelType.TEXT, true, false);
        Message message = new Message(fileUserService.findUserByEmail(user.email())
                .orElse(null).id(), channel.getId(), "test", false, null);
        channelService.createChannel(channel);
        messageService.createMessage(message);

        //when
        boolean exist = messageService.existMessageById(message.getId());

        //then
        assertTrue(exist);
        messageService.deleteMessageById(message.getId());
        fileUserService.deleteUserById(fileUserService.findUserByEmail(user.email()).orElse(null).id());
        channelService.deleteChannelById(channel.getId());

    }

    @Test
    void findMessageById() {

        //given
        UserDTO.CreateUserRequest user = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test")
                .password("test")
                .description("test")
                .build();
        fileUserService.createUser(user);
        Channel channel = new Channel("test", ChannelType.TEXT, true, false);
        Message message = new Message(fileUserService.findUserByEmail(user.email())
                .orElse(null).id(), channel.getId(), "test", false, null);
        channelService.createChannel(channel);
        messageService.createMessage(message);

        //when
        Message message1 = messageService.findMessageById(message.getId()).orElse(null);

        //then
        System.out.println(message1.toString());
        assertNotNull(message1);
        assertEquals(message1.getId(), message.getId());
        messageService.deleteMessageById(message.getId());
        fileUserService.deleteUserById(fileUserService.findUserByEmail(user.email()).orElse(null).id());
        channelService.deleteChannelById(channel.getId());

    }

    @Test
    void findChildMessagesById() {

        //given
        UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test")
                .password("test")
                .description("test")
                .build();
        fileUserService.createUser(userRequest);
        UserDTO.FindUserResult user = fileUserService.findUserByEmail(userRequest.email()).get();
        Channel channel = new Channel("test", ChannelType.DM, true, false);
        Message message = new Message(user.id(), channel.getId(), "test", false, null);
        Message message1 = new Message(user.id(), channel.getId(), "test1", true, message.getId());
        Message message2 = new Message(user.id(), channel.getId(), "test2", true, message.getId());

        channelService.createChannel(channel);
        messageService.createMessage(message);
        messageService.createMessage(message1);
        messageService.createMessage(message2);

        //when
        List<Message> messageList = messageService.findChildMessagesById(message.getId());

        //then
        messageList.forEach(m -> System.out.println(m.toString()));
        assertEquals(messageList.get(0).getId(), message1.getId());
        messageService.deleteMessageById(message.getId());
        messageService.deleteMessageById(message1.getId());
        messageService.deleteMessageById(message2.getId());
        fileUserService.deleteUserById(user.id());
        channelService.deleteChannelById(channel.getId());


    }

    @Test
    void findAllMessages() {

        //given
        UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test")
                .password("test")
                .description("test")
                .build();
        fileUserService.createUser(userRequest);
        UserDTO.FindUserResult user = fileUserService.findUserByEmail(userRequest.email()).get();
        Channel channel = new Channel("test", ChannelType.DM, true, false);
        Message message = new Message(user.id(), channel.getId(), "test", false, null);
        Message message1 = new Message(user.id(), channel.getId(), "test1", true, message.getId());
        Message message2 = new Message(user.id(), channel.getId(), "test2", true, message.getId());
        channelService.createChannel(channel);
        messageService.createMessage(message);
        messageService.createMessage(message1);
        messageService.createMessage(message2);

        //when
        List<Message> messageList = messageService.findAllMessages();

        //then
        messageList.forEach(m -> System.out.println(m.toString()));
        assertEquals(messageList.get(0).getId(), message.getId());
        messageService.deleteMessageById(message.getId());
        messageService.deleteMessageById(message1.getId());
        messageService.deleteMessageById(message2.getId());
        fileUserService.deleteUserById(user.id());
        channelService.deleteChannelById(channel.getId());

    }

    @Test
    void updateMessage() {

        //given
        UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test")
                .password("test")
                .description("test")
                .build();
        fileUserService.createUser(userRequest);
        UserDTO.FindUserResult user = fileUserService.findUserByEmail(userRequest.email()).get();
        Channel channel = new Channel("test", ChannelType.FORUM, true, false);
        Message message = new Message(user.id(), channel.getId(), "test", false, null);
        channelService.createChannel(channel);
        messageService.createMessage(message);

        //when
        Message message1 = messageService.findMessageById(message.getId()).orElse(null);
        MessageDTO.UpdateMessageRequest messageRequest = MessageDTO.UpdateMessageRequest.builder()
                .id(message1.getId())
                .content("test2")
                .isReply(false)
                .parentMessageId(null)
                .build();
        messageService.updateMessage(messageRequest);

        //then
        System.out.println(messageService.findMessageById(message.getId()).orElse(null).toString());
        assertTrue(messageService.existMessageById(message1.getId()));
        assertEquals(messageService.findMessageById(message.getId()).orElse(null).getId(), message1.getId());
        assertEquals(messageService.findMessageById(message.getId()).orElse(null).getContent(), "test2");
        messageService.deleteMessageById(message1.getId());
        fileUserService.deleteUserById(user.id());
        channelService.deleteChannelById(channel.getId());

    }

    @Test
    void deleteMessageById() {

        //given
        UserDTO.CreateUserRequest userRequest = UserDTO.CreateUserRequest.builder()
                .nickname("test")
                .email("test")
                .password("test")
                .description("test")
                .build();
        fileUserService.createUser(userRequest);
        Channel channel = new Channel("test", ChannelType.FORUM, true, false);
        Message message = new Message(fileUserService.findUserByEmail(userRequest.email())
                .orElse(null).id(), channel.getId(), "test", false, null);
        channelService.createChannel(channel);
        messageService.createMessage(message);

        //when
        messageService.deleteMessageById(message.getId());

        //then
        assertFalse(messageService.existMessageById(message.getId()));
        fileUserService.deleteUserById(fileUserService.findUserByEmail(userRequest.email())
                .orElse(null).id());
        channelService.deleteChannelById(channel.getId());

    }*/
}