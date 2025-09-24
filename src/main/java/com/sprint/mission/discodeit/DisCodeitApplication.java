package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DisCodeitApplication {

    static final String strongPassword = "fe5A3sad@lks^";

	public static void main(String[] args) {

        SpringApplication.run(DisCodeitApplication.class, args);
        //ConfigurableApplicationContext context = SpringApplication.run(DisCodeitApplication.class, args);

        //UserService userService = context.getBean(UserService.class);
        //ChannelService channelService = context.getBean(ChannelService.class);
        //MessageService messageService = context.getBean(MessageService.class);

        //testUserService(userService);
        //testChannelService(channelService);
        //testMessageService(userService, channelService, messageService);

	}

    /*public static void testUserService(UserService userService) {

        //유저 등록
        System.out.println("유저 등록");
        UserDTO.CreateUserCommand userOne = UserDTO.CreateUserCommand.builder()
                .nickname("Kim")
                .email("kimjaewon@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        UserDTO.CreateUserCommand userTwo = UserDTO.CreateUserCommand.builder()
                .nickname("Kim2")
                .email("kimjaewon2@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        userService.createUser(userOne);
        userService.createUser(userTwo);
        System.out.println("==========================");

        //유저 읽기
        System.out.println(userOne.nickname() + " 유저 읽기");
        System.out.println(userService.findUserByEmail(userOne.email())
                .orElseThrow((IllegalArgumentException::new)).toString());
        System.out.println("유저 목록 읽기");
        userService.findAllUsers()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");
        UserDTO.UpdateUserCommand requestOne = UserDTO.UpdateUserCommand.builder()
                .id(userService.findUserByEmail(userOne.email())
                        .orElseThrow((IllegalArgumentException::new)).id())
                .nickname(userOne.nickname())
                .email(userOne.email())
                .currentPassword(strongPassword)
                .newPassword(strongPassword + "k")
                .description("Bye")
                .build();
        userService.updateUser(requestOne);
        System.out.println(userOne.nickname() + " 정보 업데이트");
        System.out.println(userOne.nickname() + " 유저 읽기");
        System.out.println(userService.findUserByEmail(userOne.email())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        userService.deleteUserById(userService.findUserByEmail(userOne.email())
                .orElseThrow((IllegalArgumentException::new)).id());
        System.out.println(userTwo.nickname() + " 유저 삭제");

        System.out.println("유저 목록 읽기");
        userService.findAllUsers()
                .forEach(user -> System.out.println(user.toString()));

        System.out.println("==========================");

        //clear
        userService.findAllUsers()
                .forEach(user -> userService.deleteUserById(user.id()));

    }

    public static void testChannelService(ChannelService channelService) {

        //채널 등록
        System.out.println("채널 등록");
        //User user = new User("test", "test@test.com", strongPassword, "test");
        //Message message = new Message(user.getId(), null, "message", false, null);
        *//*Channel channelOne = new Channel.Builder()
                .channelName("channelOne")
                .category(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();
        Channel channelTwo = new Channel.Builder()
                .channelName("channelTwo")
                .category(ChannelType.VOICE)
                .isVoiceChannel(true)
                .build();*//*
        ChannelDTO.CreatePublicChannelCommand channelOne = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName("channelOne")
                .category(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();
        ChannelDTO.CreatePublicChannelCommand channelTwo = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName("channelTwo")
                .category(ChannelType.VOICE)
                .isVoiceChannel(true)
                .build();
        channelService.createChannel(channelOne);
        //channelService.addUserToChannel(channelOne.getId(), user);
        //channelService.addMessageToChannel(channelOne.getId(), message);
        channelService.createChannel(channelTwo);
        //channelService.addUserToChannel(channelTwo.getId(), user);
        //channelService.addMessageToChannel(channelTwo.getId(), message);
        System.out.println("==========================");

        //채널 읽기
        System.out.println(channelOne.channelName() + " 채널 읽기");
        System.out.println("채널 목록 읽기");
        channelService.findAllChannels()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");
        ChannelDTO.UpdateChannelCommand requestTwo = ChannelDTO.UpdateChannelCommand.builder()
                .id(channelService.findAllChannels().stream()
                        .filter(channel -> channel.channelName().equals(channelTwo.channelName()))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("No such channels")).id())
                .channelName(channelTwo.channelName())
                .category(ChannelType.DM)
                .isVoiceChannel(false)
                .build();
        channelService.updateChannel(requestTwo);
        System.out.println(channelTwo.channelName() + " 정보 업데이트");
        System.out.println(channelTwo.channelName() + " 채널 읽기");
        System.out.println(channelService.findChannelById(channelService.findAllChannels().stream()
                        .filter(channel -> channel.channelName().equals(channelTwo.channelName()))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("No such channels")).id())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        *//*channelOne = channelService.findChannelById(channelService.findAllChannels().stream()
                        .filter(channel -> channel.channelName().equals(channelOne.channelName()))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("No such channels")).id())
                .orElseThrow(() -> new IllegalArgumentException("No such users."));*//*
        System.out.println(channelOne.channelName() + " 에서 1번째 메시지 삭제");
        channelService.deleteChannelById(channelService.findAllChannels().stream()
                        .filter(channel -> channel.channelName().equals(channelTwo.channelName()))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("No such channels")).id());
        System.out.println(channelTwo.channelName() + " 채널 삭제");
        System.out.println("채널 목록 읽기");
        channelService.findAllChannels()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

        //clear
        channelService.findAllChannels()
                .forEach(channel -> channelService.deleteChannelById(channel.id()));

    }

    public static void testMessageService(UserService userService, ChannelService channelService, MessageService messageService) {

        //메시지 등록
        System.out.println("메시지 등록");
        UserDTO.CreateUserCommand userOne = UserDTO.CreateUserCommand.builder()
                .nickname("Kim")
                .email("kimjaewon@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        UserDTO.CreateUserCommand userTwo = UserDTO.CreateUserCommand.builder()
                .nickname("Kim2")
                .email("kimjaewon2@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        userService.createUser(userOne);
        userService.createUser(userTwo);
        ChannelDTO.CreatePublicChannelCommand channelOne = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName("channelOne")
                .category(ChannelType.DM)
                .isVoiceChannel(false)
                .build();
        channelService.createChannel(channelOne);
        ChannelDTO.CreatePublicChannelCommand channelTwo = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName("channelTwo")
                .category(ChannelType.VOICE)
                .isVoiceChannel(true)
                .build();
        channelService.createChannel(channelTwo);
        *//*Message messageOne = new Message.Builder()
                .userId(userService.findUserByEmail(userOne.email())
                        .orElseThrow((IllegalArgumentException::new)).id())
                .channelId(channelService.findAllChannels().stream()
                        .filter(channel -> channel.channelName().equals(channelOne.channelName()))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("No such channels")).id())
                .content("messageOne")
                .isReply(false)
                .parentMessageId(null)
                .build();
        Message messageTwo = new Message.Builder()
                .userId(userService.findUserByEmail(userTwo.email())
                        .orElseThrow((IllegalArgumentException::new)).id())
                .channelId(channelService.findAllChannels().stream()
                        .filter(channel -> channel.channelName().equals(channelTwo.channelName()))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("No such channels")).id())
                .content("messageTwo")
                .isReply(true)
                .parentMessageId(messageOne.getId())
                .build();*//*
        MessageDTO.CreateMessageCommand messageOne = MessageDTO.CreateMessageCommand.builder()
                .userId(userService.findUserByEmail(userOne.email())
                        .orElseThrow((IllegalArgumentException::new)).id())
                .channelId(channelService.findAllChannels().stream()
                        .filter(channel -> channel.channelName().equals(channelOne.channelName()))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("No such channels")).id())
                .content("messageOne")
                .isReply(false)
                .parentMessageId(null)
                .build();
        messageService.createMessage(messageOne);
        MessageDTO.CreateMessageCommand messageTwo = MessageDTO.CreateMessageCommand.builder()
                .userId(userService.findUserByEmail(userTwo.email())
                        .orElseThrow((IllegalArgumentException::new)).id())
                .channelId(channelService.findAllChannels().stream()
                        .filter(channel -> channel.channelName().equals(channelTwo.channelName()))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("No such channels")).id())
                .content("messageTwo")
                .isReply(true)
                .parentMessageId(messageService.findAllMessages().get(0).id())
                .build();
        messageService.createMessage(messageTwo);
        System.out.println("==========================");

        //메시지 읽기
        System.out.println("메시지 읽기");
        System.out.println("메시지 목록 읽기");
        messageService.findAllMessages()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

        //메시지 수정
        System.out.println("메시지 수정");
        MessageDTO.FindMessageResult findMessageResult = messageService.findMessagesByChannelId(channelService.findAllChannels().stream()
                .filter(channel -> channel.channelName().equals(channelOne.channelName()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No such channels")).id()).get(0);
        MessageDTO.UpdateMessageCommand requestOne = MessageDTO.UpdateMessageCommand.builder()
                .id(findMessageResult.id())
                .content("messageOne edited")
                .isReply(messageOne.isReply())
                .parentMessageId(findMessageResult.parentMessageId())
                .build();
        messageService.updateMessage(requestOne);
        System.out.println(findMessageResult.id() + " 정보 업데이트");
        System.out.println(findMessageResult.id() + " 메시지 읽기");
        System.out.println(messageService.findMessageById(findMessageResult.id())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");
        MessageDTO.FindMessageResult findMessageResultTwo = messageService.findMessagesByChannelId(channelService.findAllChannels().stream()
                .filter(channel -> channel.channelName().equals(channelTwo.channelName()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No such channels")).id()).get(0);
        messageService.deleteMessageById(findMessageResultTwo.id());
        System.out.println(findMessageResultTwo.id() + " 메시지 삭제");
        System.out.println("메시지 목록 읽기");
        messageService.findAllMessages()
                .forEach(message -> System.out.println(message.toString()));

        System.out.println("==========================");

        //clear
        messageService.findAllMessages()
                .forEach(message -> messageService.deleteMessageById(message.id()));
        channelService.findAllChannels()
                .forEach(channel -> channelService.deleteChannelById(channel.id()));
        userService.findAllUsers()
                .forEach(user -> userService.deleteUserById(user.id()));

    }*/

}
