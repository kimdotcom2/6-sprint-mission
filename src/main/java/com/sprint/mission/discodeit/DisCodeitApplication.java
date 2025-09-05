package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class DisCodeitApplication {

    static final String strongPassword = "fe5A3sad@lks^";

	public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(DisCodeitApplication.class, args);

        UserService userService = context.getBean(UserService.class);
        ChannelService channelService = context.getBean(ChannelService.class);
        MessageService messageService = context.getBean(MessageService.class);

        testMessageService(userService, channelService, messageService);

	}

    public static void testUserService(UserService userService) {

        //유저 등록
        System.out.println("유저 등록");
        UserDTO.CreateUserRequest userOne = UserDTO.CreateUserRequest.builder()
                .nickname("Kim")
                .email("kimjaewon@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        UserDTO.CreateUserRequest userTwo = UserDTO.CreateUserRequest.builder()
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
        UserDTO.UpdateUserRequest requestOne = UserDTO.UpdateUserRequest.builder()
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
        /*Channel channelOne = new Channel.Builder()
                .channelName("channelOne")
                .category(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();
        Channel channelTwo = new Channel.Builder()
                .channelName("channelTwo")
                .category(ChannelType.VOICE)
                .isVoiceChannel(true)
                .build();*/
        ChannelDTO.CreatePublicChannelRequest channelOne = ChannelDTO.CreatePublicChannelRequest.builder()
                .channelName("channelOne")
                .category(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();
        ChannelDTO.CreatePublicChannelRequest channelTwo = ChannelDTO.CreatePublicChannelRequest.builder()
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
        ChannelDTO.UpdateChannelRequest requestTwo = ChannelDTO.UpdateChannelRequest.builder()
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
        /*channelOne = channelService.findChannelById(channelService.findAllChannels().stream()
                        .filter(channel -> channel.channelName().equals(channelOne.channelName()))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException("No such channels")).id())
                .orElseThrow(() -> new IllegalArgumentException("No such users."));*/
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
        UserDTO.CreateUserRequest userOne = UserDTO.CreateUserRequest.builder()
                .nickname("Kim")
                .email("kimjaewon@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        UserDTO.CreateUserRequest userTwo = UserDTO.CreateUserRequest.builder()
                .nickname("Kim2")
                .email("kimjaewon2@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        userService.createUser(userOne);
        userService.createUser(userTwo);
        ChannelDTO.CreatePublicChannelRequest channelOne = ChannelDTO.CreatePublicChannelRequest.builder()
                .channelName("channelOne")
                .category(ChannelType.DM)
                .isVoiceChannel(false)
                .build();
        channelService.createChannel(channelOne);
        ChannelDTO.CreatePublicChannelRequest channelTwo = ChannelDTO.CreatePublicChannelRequest.builder()
                .channelName("channelTwo")
                .category(ChannelType.VOICE)
                .isVoiceChannel(true)
                .build();
        channelService.createChannel(channelTwo);
        Message messageOne = new Message.Builder()
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
                .build();
        messageService.createMessage(messageOne);
        messageService.createMessage(messageTwo);
        System.out.println("==========================");

        //메시지 읽기
        System.out.println("메시지 읽기");
        System.out.println(messageOne.getId() + " 메시지 읽기");
        System.out.println(messageService.findMessageById(messageOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        messageService.findChildMessagesById(messageOne.getId())
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("메시지 목록 읽기");
        messageService.findAllMessages()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

        //메시지 수정
        System.out.println("메시지 수정");
        MessageDTO.UpdateMessageRequest requestOne = MessageDTO.UpdateMessageRequest.builder()
                .id(messageOne.getId())
                .content("messageOne edited")
                .isReply(messageOne.isReply())
                .parentMessageId(messageOne.getParentMessageId())
                .build();
        messageService.updateMessage(requestOne);
        System.out.println(messageOne.getId() + " 정보 업데이트");
        System.out.println(messageOne.getId() + " 메시지 읽기");
        System.out.println(messageService.findMessageById(messageOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");
        messageService.deleteMessageById(messageTwo.getId());
        System.out.println(messageTwo.getId() + " 메시지 삭제");
        System.out.println("메시지 목록 읽기");
        messageService.findAllMessages()
                .forEach(message -> System.out.println(message.toString()));

        System.out.println("==========================");

        //clear
        messageService.findAllMessages()
                .forEach(message -> messageService.deleteMessageById(message.getId()));
        channelService.findAllChannels()
                .forEach(channel -> channelService.deleteChannelById(channel.id()));
        userService.findAllUsers()
                .forEach(user -> userService.deleteUserById(user.id()));

    }

}
