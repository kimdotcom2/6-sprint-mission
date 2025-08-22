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
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.nio.file.Path;

import static com.sprint.mission.discodeit.config.PathConfig.FILE_PATH;

public class JavaApplication {

    static String fileDirectory = FILE_PATH;

    public static void main(String[] args) {

        /*testJcfUserService();
        testJcfChannelService();
        testJcfMessageService();
        testFileUserService();
        testFileChannelService();
        testFileMessageService();*/
        testBasicUserService();
        testBasicChannelService();
        testBasicMessageService();

    }

    public static void testJcfUserService() {

        UserService jcfUserCrudService = new JCFUserService();

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User.Builder()
                .nickname("Kim")
                .email("kimjaewon@gmail.com")
                .password("1234")
                .description("Hi")
                .build();
        User userTwo = new User.Builder()
                .nickname("Kim2")
                .email("kimjaewon2@gmail.com")
                .password("1234")
                .description("Hi")
                .build();
        jcfUserCrudService.createUser(userOne);
        jcfUserCrudService.createUser(userTwo);
        System.out.println("==========================");

        //유저 읽기
        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(jcfUserCrudService.findUserById(userOne.getId())
                    .orElseThrow((IllegalArgumentException::new)).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No user found with id: " + userOne.getId());
        }
        System.out.println("유저 목록 읽기");
        jcfUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");
        try {
            DiscordDTO.UpdateUserRequest requestOne = new DiscordDTO.UpdateUserRequest.Builder()
                    .id(userOne.getId())
                    .nickname(userOne.getNickname())
                    .email(userOne.getEmail())
                    .currentPassword("1234")
                    .newPassword("12345")
                    .description("Bye")
                    .build();
            jcfUserCrudService.updateUser(requestOne);
            System.out.println(userOne.getNickname() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(jcfUserCrudService.findUserById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No user found with id: " + userOne.getId());
        }
        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        try {
            jcfUserCrudService.deleteUserById(userTwo.getId());
            System.out.println(userTwo.getNickname() + " 유저 삭제");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.println("유저 목록 읽기");
        jcfUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));

        System.out.println("==========================");

    }

    public static void testFileUserService() {

        UserService fileUserCrudService = new FileUserService(Path.of(fileDirectory + "users"));

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User.Builder()
                .nickname("Kim")
                .email("kimjaewon@gmail.com")
                .password("1234")
                .description("Hi")
                .build();
        User userTwo = new User.Builder()
                .nickname("Kim2")
                .email("kimjaewon2@gmail.com")
                .password("1234")
                .description("Hi")
                .build();
        fileUserCrudService.createUser(userOne);
        fileUserCrudService.createUser(userTwo);
        System.out.println("==========================");

        //유저 읽기
        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(fileUserCrudService.findUserById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No user found with id: " + userOne.getId());
        }
        System.out.println("유저 목록 읽기");
        fileUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");
        try {
            DiscordDTO.UpdateUserRequest requestOne = new DiscordDTO.UpdateUserRequest.Builder()
                    .id(userOne.getId())
                    .nickname(userOne.getNickname())
                    .email(userOne.getEmail())
                    .currentPassword("1234")
                    .newPassword("12345")
                    .description("Bye")
                    .build();
            fileUserCrudService.updateUser(requestOne);
            System.out.println(userOne.getNickname() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(fileUserCrudService.findUserById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No user found with id: " + userOne.getId());
        }
        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        try {
            fileUserCrudService.deleteUserById(userOne.getId());
            fileUserCrudService.deleteUserById(userTwo.getId());
            System.out.println(userTwo.getNickname() + " 유저 삭제");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println("유저 목록 읽기");
        fileUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

    }

    public static void testBasicUserService() {

        //UserRepository jcfUserRepository = new JCFUserRepository();
        UserRepository fileUserRepository = new FileUserRepository("users");
        UserService basicUserCrudService = new BasicUserService(fileUserRepository);

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User.Builder()
                .nickname("Kim")
                .email("kimjaewon@gmail.com")
                .password("1234")
                .description("Hi")
                .build();
        User userTwo = new User.Builder()
                .nickname("Kim2")
                .email("kimjaewon2@gmail.com")
                .password("1234")
                .description("Hi")
                .build();
        basicUserCrudService.createUser(userOne);
        basicUserCrudService.createUser(userTwo);
        System.out.println("==========================");

        //유저 읽기
        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(basicUserCrudService.findUserById(userOne.getId())
                    .orElseThrow((IllegalArgumentException::new)).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No user found with id: " + userOne.getId());
        }
        System.out.println("유저 목록 읽기");
        basicUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");
        try {
            DiscordDTO.UpdateUserRequest requestOne = new DiscordDTO.UpdateUserRequest.Builder()
                    .id(userOne.getId())
                    .nickname(userOne.getNickname())
                    .email(userOne.getEmail())
                    .currentPassword("1234")
                    .newPassword("12345")
                    .description("Bye")
                    .build();
            basicUserCrudService.updateUser(requestOne);
            System.out.println(userOne.getNickname() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(basicUserCrudService.findUserById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No user found with id: " + userOne.getId());
        }
        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        try {
            basicUserCrudService.deleteUserById(userTwo.getId());
            System.out.println(userTwo.getNickname() + " 유저 삭제");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.println("유저 목록 읽기");
        basicUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));

        System.out.println("==========================");

    }

    public static void testJcfChannelService() {

        ChannelService jcfChannelCrudService = new JCFChannelService();

        //채널 등록
        System.out.println("채널 등록");
        User user = new User("test", "test", "test", "test");
        Message message = new Message(user.getId(), null, "message", false, null);
        //Channel channelOne = new Channel("channelOne", ChannelType.TEXT, false);
        //Channel channelTwo = new Channel("channelTwo", ChannelType.VOICE, true);
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
        jcfChannelCrudService.createChannel(channelOne);
        jcfChannelCrudService.addUserToChannel(channelOne.getId(), user);
        jcfChannelCrudService.addMessageToChannel(channelOne.getId(), message);
        jcfChannelCrudService.createChannel(channelTwo);
        jcfChannelCrudService.addUserToChannel(channelTwo.getId(), user);
        jcfChannelCrudService.addMessageToChannel(channelTwo.getId(), message);
        System.out.println("==========================");

        //채널 읽기
        try {
            System.out.println(channelOne.getChannelName() + " 채널 읽기");
            System.out.println(jcfChannelCrudService.findChannelById(channelOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No channel found with id: " + channelOne.getId());
        }
        System.out.println("채널 목록 읽기");
        jcfChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");
        try {
            DiscordDTO.UpdateChannelRequest requestTwo = new DiscordDTO.UpdateChannelRequest.Builder()
                    .id(channelTwo.getId())
                    .channelName(channelTwo.getChannelName())
                    .category(ChannelType.DM)
                    .isVoiceChannel(false)
                    .build();
            jcfChannelCrudService.updateChannel(requestTwo);
            System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(channelTwo.getChannelName() + " 채널 읽기");
            System.out.println(jcfChannelCrudService.findChannelById(channelTwo.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No channel found with id: " + channelOne.getId() + "");
        }
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        System.out.println(channelOne.getChannelName() + " 에서 "
                + channelOne.getUserMap().get(user.getId()).getNickname() + " 유저 삭제");
        jcfChannelCrudService.deleteUserFromChannel(channelOne.getId(), channelOne.getUserMap().get(user.getId()).getId());
        System.out.println(channelOne.getChannelName() + " 에서 1번째 메시지 삭제");
        jcfChannelCrudService.deleteMessageFromChannel(channelOne.getId(), channelOne.getMessageMap().get(message.getId()).getId());
        try {
            jcfChannelCrudService.deleteChannelById(channelTwo.getId());
            System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println("채널 목록 읽기");
        jcfChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

    }

    public static void testFileChannelService() {

        ChannelService fileChannelCrudService = new FileChannelService(Path.of(fileDirectory + "channels"));

        //채널 등록
        System.out.println("채널 등록");
        User user = new User("test", "test", "test", "test");
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
        fileChannelCrudService.createChannel(channelOne);
        fileChannelCrudService.addUserToChannel(channelOne.getId(), user);
        fileChannelCrudService.addMessageToChannel(channelOne.getId(), message);
        fileChannelCrudService.createChannel(channelTwo);
        fileChannelCrudService.addUserToChannel(channelTwo.getId(), user);
        fileChannelCrudService.addMessageToChannel(channelTwo.getId(), message);
        System.out.println("==========================");

        //채널 읽기
        try {
            System.out.println(channelOne.getChannelName() + " 채널 읽기");
            System.out.println(fileChannelCrudService.findChannelById(channelOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No channel found with id: " + channelOne.getId());
        }
        System.out.println("채널 목록 읽기");
        fileChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");
        try {
            DiscordDTO.UpdateChannelRequest requestTwo = new DiscordDTO.UpdateChannelRequest.Builder()
                    .id(channelTwo.getId())
                    .channelName(channelTwo.getChannelName())
                    .category(ChannelType.DM)
                    .isVoiceChannel(false)
                    .build();
            fileChannelCrudService.updateChannel(requestTwo);
            System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            channelOne = fileChannelCrudService.findChannelById(channelOne.getId())
                    .orElseThrow(IllegalArgumentException::new);
            channelTwo = fileChannelCrudService.findChannelById(channelTwo.getId())
                    .orElseThrow(IllegalArgumentException::new);
            System.out.println(channelTwo.getChannelName() + " 채널 읽기");
            System.out.println(fileChannelCrudService.findChannelById(channelTwo.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No channel found with id: " + channelOne.getId() + "");
        }
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        System.out.println(channelOne.getChannelName() + " 에서 "
                + channelOne.getUserMap().get(user.getId()).getNickname() + " 유저 삭제");
        fileChannelCrudService.deleteUserFromChannel(channelOne.getId(), user.getId());
        System.out.println(channelOne.getChannelName() + " 에서 1번째 메시지 삭제");
        fileChannelCrudService.deleteMessageFromChannel(channelOne.getId(), message.getId());
        channelOne.getMessageMap().entrySet().forEach(m -> System.out.println(m.toString()));
        try {
            fileChannelCrudService.deleteChannelById(channelTwo.getId());
            System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println("채널 목록 읽기");
        fileChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

    }

    public static void testBasicChannelService() {

        //ChannelRepository jcfChannelRepository = new JCFChannelRepository();
        ChannelRepository fileChannelRepository = new FileChannelRepository("channels");
        ChannelService basicChannelCrudService = new BasicChannelService(fileChannelRepository);

        //채널 등록
        System.out.println("채널 등록");
        User user = new User("test", "test", "test", "test");
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
        basicChannelCrudService.createChannel(channelOne);
        basicChannelCrudService.addUserToChannel(channelOne.getId(), user);
        basicChannelCrudService.addMessageToChannel(channelOne.getId(), message);
        basicChannelCrudService.createChannel(channelTwo);
        basicChannelCrudService.addUserToChannel(channelTwo.getId(), user);
        basicChannelCrudService.addMessageToChannel(channelTwo.getId(), message);
        System.out.println("==========================");

        //채널 읽기
        try {
            System.out.println(channelOne.getChannelName() + " 채널 읽기");
            System.out.println(basicChannelCrudService.findChannelById(channelOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No channel found with id: " + channelOne.getId());
        }
        System.out.println("채널 목록 읽기");
        basicChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");
        try {
            DiscordDTO.UpdateChannelRequest requestTwo = new DiscordDTO.UpdateChannelRequest.Builder()
                    .id(channelTwo.getId())
                    .channelName(channelTwo.getChannelName())
                    .category(ChannelType.DM)
                    .isVoiceChannel(false)
                    .build();
            basicChannelCrudService.updateChannel(requestTwo);
            System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
            channelTwo = basicChannelCrudService.findChannelById(channelTwo.getId())
                    .orElseThrow(() ->  new IllegalArgumentException("No such channels"));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(channelTwo.getChannelName() + " 채널 읽기");
            System.out.println(basicChannelCrudService.findChannelById(channelTwo.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No channel found with id: " + channelOne.getId() + "");
        }
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        channelOne = basicChannelCrudService.findChannelById(channelOne.getId())
                        .orElseThrow(() -> new IllegalArgumentException("No such users."));
        System.out.println(channelOne.getChannelName() + " 에서 "
                + user.getNickname() + " 유저 삭제");
        basicChannelCrudService.deleteUserFromChannel(channelOne.getId(), channelOne.getUserMap().get(user.getId()).getId());
        System.out.println(channelOne.getChannelName() + " 에서 1번째 메시지 삭제");
        basicChannelCrudService.deleteMessageFromChannel(channelOne.getId(), channelOne.getMessageMap().get(message.getId()).getId());
        try {
            basicChannelCrudService.deleteChannelById(channelTwo.getId());
            System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println("채널 목록 읽기");
        basicChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

    }

    public static void testJcfMessageService() {

        UserService jcfUserCrudService = new JCFUserService();
        ChannelService jcfChannelCrudService = new JCFChannelService();
        MessageService jcfMessageCrudService = new JCFMessageService(jcfUserCrudService, jcfChannelCrudService);

        //메시지 등록
        System.out.println("메시지 등록");
        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        jcfUserCrudService.createUser(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        jcfUserCrudService.createUser(userTwo);
        Channel channelOne = new Channel("channelOne", ChannelType.DM, false);
        jcfChannelCrudService.createChannel(channelOne);
        Channel channelTwo = new Channel("channelTwo", ChannelType.VOICE, true);
        jcfChannelCrudService.createChannel(channelTwo);
        //Message messageOne = new Message(userOne.getId(), channelOne.getId(),"messageOne", false, null);
        //Message messageTwo = new Message(userOne.getId(), channelOne.getId(), "messageTwo", true, messageOne.getId());
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
        jcfMessageCrudService.createMessage(messageOne);
        jcfMessageCrudService.createMessage(messageTwo);
        System.out.println("==========================");

        //메시지 읽기
        System.out.println("메시지 읽기");
        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(jcfMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No message found with id: " + messageOne.getId());
        }
        try {
            System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        jcfMessageCrudService.findChildMessagesById(messageOne.getId()).stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("메시지 목록 읽기");
        jcfMessageCrudService.findAllMessages().stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

        //메시지 수정
        System.out.println("메시지 수정");
        try {
            DiscordDTO.UpdateMessageRequest requestOne = new DiscordDTO.UpdateMessageRequest.Builder()
                    .id(messageOne.getId())
                    .content("messageOne edited")
                    .isReply(messageOne.isReply())
                    .parentMessageId(messageOne.getParentMessageId())
                    .build();
            jcfMessageCrudService.updateMessage(requestOne);
            System.out.println(messageOne.getId() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(jcfMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No message found with id: " + messageOne.getId());
        }
        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");
        try {
            jcfMessageCrudService.deleteMessageById(messageTwo.getId());
            System.out.println(messageTwo.getId() + " 메시지 삭제");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println("메시지 목록 읽기");
        jcfMessageCrudService.findAllMessages().stream()
                .forEach(message -> System.out.println(message.toString()));

        System.out.println("==========================");

    }

    public static void testFileMessageService() {

        UserService fileUserCrudService = new FileUserService(Path.of(fileDirectory + "users"));
        ChannelService fileChannelCrudService = new FileChannelService(Path.of(fileDirectory + "channels"));
        MessageService fileMessageCrudService = new FileMessageService(Path.of(fileDirectory + "messages"), fileUserCrudService, fileChannelCrudService);

        //메시지 등록
        System.out.println("메시지 등록");
        User userOne = new User("Kim", "kimjaewon3@gmail.com", "1234", "Hi");
        fileUserCrudService.createUser(userOne);
        User userTwo = new User("Kim2", "kimjaewon4@gmail.com", "1234", "Hi");
        fileUserCrudService.createUser(userTwo);
        Channel channelOne = new Channel("channelOne", ChannelType.FORUM, false);
        fileChannelCrudService.createChannel(channelOne);
        Channel channelTwo = new Channel("channelTwo", ChannelType.DM, true);
        fileChannelCrudService.createChannel(channelTwo);
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
        fileMessageCrudService.createMessage(messageOne);
        fileMessageCrudService.createMessage(messageTwo);
        System.out.println("==========================");

        //메시지 읽기
        System.out.println("메시지 읽기");
        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(fileMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No message found with id: " + messageOne.getId() + "");
        }
        try {
            System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
            fileMessageCrudService.findChildMessagesById(messageOne.getId()).stream()
                    .forEach(message -> System.out.println(message.toString()));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println("메시지 목록 읽기");
        fileMessageCrudService.findAllMessages().stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

        //메시지 수정
        System.out.println("메시지 수정");
        try {
            DiscordDTO.UpdateMessageRequest requestOne = new DiscordDTO.UpdateMessageRequest.Builder()
                    .id(messageOne.getId())
                    .content("messageOne edited")
                    .isReply(messageOne.isReply())
                    .parentMessageId(messageOne.getParentMessageId())
                    .build();
            fileMessageCrudService.updateMessage(requestOne);
            System.out.println(messageOne.getId() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(fileMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No message found with id: " + messageOne.getId() + "");
        }
        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");
        try {
            fileMessageCrudService.deleteMessageById(messageTwo.getId());
            System.out.println(messageTwo.getId() + " 메시지 삭제");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println("메시지 목록 읽기");
        fileMessageCrudService.findAllMessages().stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

    }

    public static void testBasicMessageService() {

        UserRepository jcfUserRepository = new JCFUserRepository();
        ChannelRepository jcfChannelRepository = new JCFChannelRepository();
        //MessageRepository jcfMessageRepository = new JCFMessageRepository();
        MessageRepository fileMessageRepository = new FileMessageRepository("messages");
        UserService basicUserCrudService = new BasicUserService(jcfUserRepository);
        ChannelService basicChannelCrudService = new BasicChannelService(jcfChannelRepository);
        MessageService basicMessageCrudService = new BasicMessageService(jcfUserRepository, jcfChannelRepository, fileMessageRepository);

        //메시지 등록
        System.out.println("메시지 등록");
        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        basicUserCrudService.createUser(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        basicUserCrudService.createUser(userTwo);
        Channel channelOne = new Channel("channelOne", ChannelType.DM, false);
        basicChannelCrudService.createChannel(channelOne);
        Channel channelTwo = new Channel("channelTwo", ChannelType.VOICE, true);
        basicChannelCrudService.createChannel(channelTwo);
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
        basicMessageCrudService.createMessage(messageOne);
        basicMessageCrudService.createMessage(messageTwo);
        System.out.println("==========================");

        //메시지 읽기
        System.out.println("메시지 읽기");
        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(basicMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No message found with id: " + messageOne.getId());
        }
        try {
            System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        basicMessageCrudService.findChildMessagesById(messageOne.getId()).stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("메시지 목록 읽기");
        basicMessageCrudService.findAllMessages().stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

        //메시지 수정
        System.out.println("메시지 수정");
        try {
            DiscordDTO.UpdateMessageRequest requestOne = new DiscordDTO.UpdateMessageRequest.Builder()
                    .id(messageOne.getId())
                    .content("messageOne edited")
                    .isReply(messageOne.isReply())
                    .parentMessageId(messageOne.getParentMessageId())
                    .build();
            basicMessageCrudService.updateMessage(requestOne);
            System.out.println(messageOne.getId() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(basicMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No message found with id: " + messageOne.getId());
        }
        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");
        try {
            basicMessageCrudService.deleteMessageById(messageTwo.getId());
            System.out.println(messageTwo.getId() + " 메시지 삭제");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println("메시지 목록 읽기");
        basicMessageCrudService.findAllMessages().stream()
                .forEach(message -> System.out.println(message.toString()));

        System.out.println("==========================");

    }

}
