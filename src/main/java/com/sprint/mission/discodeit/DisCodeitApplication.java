package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.DiscordDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        User userOne = new User.Builder()
                .nickname("Kim")
                .email("kimjaewon@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        User userTwo = new User.Builder()
                .nickname("Kim2")
                .email("kimjaewon2@gmail.com")
                .password(strongPassword)
                .description("Hi")
                .build();
        userService.createUser(userOne);
        userService.createUser(userTwo);
        System.out.println("==========================");

        //유저 읽기
        System.out.println(userOne.getNickname() + " 유저 읽기");
        System.out.println(userService.findUserById(userOne.getId())
                .orElseThrow((IllegalArgumentException::new)).toString());
        System.out.println("유저 목록 읽기");
        userService.findAllUsers()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");
        DiscordDTO.UpdateUserRequest requestOne = DiscordDTO.UpdateUserRequest.builder()
                .id(userOne.getId())
                .nickname(userOne.getNickname())
                .email(userOne.getEmail())
                .currentPassword(strongPassword)
                .newPassword(strongPassword + "k")
                .description("Bye")
                .build();
        userService.updateUser(requestOne);
        System.out.println(userOne.getNickname() + " 정보 업데이트");
        System.out.println(userOne.getNickname() + " 유저 읽기");
        System.out.println(userService.findUserById(userOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        userService.deleteUserById(userTwo.getId());
        System.out.println(userTwo.getNickname() + " 유저 삭제");

        System.out.println("유저 목록 읽기");
        userService.findAllUsers()
                .forEach(user -> System.out.println(user.toString()));

        System.out.println("==========================");

        //clear
        userService.findAllUsers()
                .forEach(user -> userService.deleteUserById(user.getId()));

    }

    public static void testChannelService(ChannelService channelService) {

        //채널 등록
        System.out.println("채널 등록");
        User user = new User("test", "test@test.com", strongPassword, "test");
        Message message = new Message(user.getId(), null, "message", false, null);
        Channel channelOne = new Channel.Builder()
                .channelName("channelOne")
                .category(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();
        Channel channelTwo = new Channel.Builder()
                .channelName("channelTwo")
                .category(ChannelType.VOICE)
                .isVoiceChannel(true)
                .build();
        channelService.createChannel(channelOne);
        channelService.addUserToChannel(channelOne.getId(), user);
        channelService.addMessageToChannel(channelOne.getId(), message);
        channelService.createChannel(channelTwo);
        channelService.addUserToChannel(channelTwo.getId(), user);
        channelService.addMessageToChannel(channelTwo.getId(), message);
        System.out.println("==========================");

        //채널 읽기
        System.out.println(channelOne.getChannelName() + " 채널 읽기");
        System.out.println(channelService.findChannelById(channelOne.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("채널 목록 읽기");
        channelService.findAllChannels()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");
        DiscordDTO.UpdateChannelRequest requestTwo = DiscordDTO.UpdateChannelRequest.builder()
                .id(channelTwo.getId())
                .channelName(channelTwo.getChannelName())
                .category(ChannelType.DM)
                .isVoiceChannel(false)
                .build();
        channelService.updateChannel(requestTwo);
        System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        channelTwo = channelService.findChannelById(channelTwo.getId())
                .orElseThrow(() ->  new IllegalArgumentException("No such channels"));
        System.out.println(channelTwo.getChannelName() + " 채널 읽기");
        System.out.println(channelService.findChannelById(channelTwo.getId())
                .orElseThrow(IllegalArgumentException::new).toString());
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        channelOne = channelService.findChannelById(channelOne.getId())
                .orElseThrow(() -> new IllegalArgumentException("No such users."));
        System.out.println(channelOne.getChannelName() + " 에서 "
                + user.getNickname() + " 유저 삭제");
        channelService.deleteUserFromChannel(channelOne.getId(), channelOne.getUserMap().get(user.getId()).getId());
        System.out.println(channelOne.getChannelName() + " 에서 1번째 메시지 삭제");
        channelService.deleteMessageFromChannel(channelOne.getId(), channelOne.getMessageMap().get(message.getId()).getId());
        channelService.deleteChannelById(channelTwo.getId());
        System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        System.out.println("채널 목록 읽기");
        channelService.findAllChannels()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

        //clear
        channelService.findAllChannels()
                .forEach(channel -> channelService.deleteChannelById(channel.getId()));

    }

    public static void testMessageService(UserService userService, ChannelService channelService, MessageService messageService) {

        //메시지 등록
        System.out.println("메시지 등록");
        User userOne = new User("Kim", "kimjaewon@gmail.com", strongPassword, "Hi");
        userService.createUser(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", strongPassword, "Hi");
        userService.createUser(userTwo);
        Channel channelOne = new Channel("channelOne", ChannelType.DM, false);
        channelService.createChannel(channelOne);
        Channel channelTwo = new Channel("channelTwo", ChannelType.VOICE, true);
        channelService.createChannel(channelTwo);
        Message messageOne = new Message.Builder()
                .userId(userOne.getId())
                .channelId(channelOne.getId())
                .content("messageOne")
                .isReply(false)
                .parentMessageId(null)
                .build();
        Message messageTwo = new Message.Builder()
                .userId(userTwo.getId())
                .channelId(channelTwo.getId())
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
        DiscordDTO.UpdateMessageRequest requestOne = DiscordDTO.UpdateMessageRequest.builder()
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
                .forEach(channel -> channelService.deleteChannelById(channel.getId()));
        userService.findAllUsers()
                .forEach(user -> userService.deleteUserById(user.getId()));

    }

}
