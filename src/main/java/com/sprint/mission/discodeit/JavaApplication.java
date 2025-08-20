package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.file.FileChannelCrudService;
import com.sprint.mission.discodeit.service.file.FileMessageCrudService;
import com.sprint.mission.discodeit.service.file.FileUserCrudService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelCrudService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageCrudService;
import com.sprint.mission.discodeit.service.jcf.JCFUserCrudService;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JavaApplication {

    static String fileDirectory = "C:\\spring\\codeit-bootcamp-spring\\6-sprint-mission\\src\\main\\resources\\data\\";

    public static void main(String[] args) {

        testJcfUserService();
        testJcfChannelService();
        testJcfMessageService();
        testFileUserService();
        testFileChannelService();
        testFileMessageService();

    }

    public static void testJcfUserService() {

        UserService jcfUserCrudService = new JCFUserCrudService();

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        jcfUserCrudService.create(userOne);
        System.out.println(userOne.getNickname() + " 유저 추가");
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        jcfUserCrudService.create(userTwo);
        System.out.println(userTwo.getNickname() + " 유저 추가");

        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(jcfUserCrudService.findUserById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }

        System.out.println("유저 목록 읽기");
        jcfUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");

        try {
            jcfUserCrudService.update(userOne.getId(), userOne.getNickname(), userOne.getEmail(), userOne.getPassword(), "Bye");
            System.out.println(userOne.getNickname() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }

        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(jcfUserCrudService.findUserById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }

        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        try {
            jcfUserCrudService.deleteById(userTwo.getId());
            System.out.println(userTwo.getNickname() + " 유저 삭제");
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }

        System.out.println("유저 목록 읽기");
        jcfUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));

        System.out.println("==========================");

    }

    public static void testFileUserService() {

        UserService fileUserCrudService = new FileUserCrudService(Path.of(fileDirectory + "users"));

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        fileUserCrudService.create(userOne);
        System.out.println(userOne.getNickname() + " 유저 추가");
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        fileUserCrudService.create(userTwo);
        System.out.println(userTwo.getNickname() + " 유저 추가");

        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(fileUserCrudService.findUserById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }

        System.out.println("유저 목록 읽기");
        fileUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");

        try {
            fileUserCrudService.update(userOne.getId(), userOne.getNickname(), userOne.getEmail(), userOne.getPassword(), "Bye");
            System.out.println(userOne.getNickname() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }

        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(fileUserCrudService.findUserById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }

        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");

        try {
            fileUserCrudService.deleteById(userTwo.getId());
            System.out.println(userTwo.getNickname() + " 유저 삭제");
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }

        System.out.println("유저 목록 읽기");
        fileUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));

        System.out.println("==========================");

    }

    public static void testJcfChannelService() {

        ChannelService jcfChannelCrudService = new JCFChannelCrudService();

        //채널 등록
        User user = new User("test", "test", "test", "test");
        Message message = new Message(user.getId(), null, "message", false, null);
        Map<UUID, User> userMap = new HashMap<>();
        userMap.put(user.getId(), user);
        Map<UUID, Message> messageMap = new HashMap<>();
        messageMap.put(message.getId(), message);

        System.out.println("채널 등록");
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        channelOne.setUserMap(userMap);
        channelOne.setMessageMap(messageMap);
        jcfChannelCrudService.create(channelOne);
        System.out.println(channelOne.getChannelName() + " 채널 등록");
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        channelTwo.setUserMap(userMap);
        channelTwo.setMessageMap(messageMap);
        jcfChannelCrudService.create(channelTwo);

        System.out.println(channelTwo.getChannelName() + " 채널 등록");
        try {
            System.out.println(channelOne.getChannelName() + " 채널 읽기");
            System.out.println(jcfChannelCrudService.findChannelById(channelOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }

        System.out.println("채널 목록 읽기");
        jcfChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");

        try {
            jcfChannelCrudService.update(channelTwo.getId(), channelTwo.getChannelName(), channelTwo.getCategory(), false);
            System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }

        try {
            System.out.println(channelTwo.getChannelName() + " 채널 읽기");
            System.out.println(jcfChannelCrudService.findChannelById(channelTwo.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }

        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");

        System.out.println(channelOne.getChannelName() + " 에서 "
        + channelOne.getUserMap().get(user.getId()).getNickname() + " 유저 삭제");
        jcfChannelCrudService.deleteUserFromChannel(channelOne.getId(), channelOne.getUserMap().get(user.getId()).getId());

        System.out.println(channelOne.getChannelName() + " 에서 1번째 메시지 삭제");
        jcfChannelCrudService.deleteMessageFromChannel(channelOne.getId(), channelOne.getMessageMap().get(message.getId()).getId());
        channelOne.getMessageMap().entrySet().forEach(m -> System.out.println(m.toString()));

        try {
            jcfChannelCrudService.deleteById(channelTwo.getId());
            System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }

        System.out.println("채널 목록 읽기");
        jcfChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

    }

    public static void testFileChannelService() {

        ChannelService fileChannelCrudService = new FileChannelCrudService(Path.of(fileDirectory + "channels"));

        //채널 등록
        User user = new User("test", "test", "test", "test");
        Message message = new Message(user.getId(), null, "message", false, null);
        Map<UUID, User> userMap = new HashMap<>();
        userMap.put(user.getId(), user);
        Map<UUID, Message> messageMap = new HashMap<>();
        messageMap.put(message.getId(), message);

        System.out.println("채널 등록");
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        channelOne.setUserMap(userMap);
        channelOne.setMessageMap(messageMap);
        fileChannelCrudService.create(channelOne);
        System.out.println(channelOne.getChannelName() + " 채널 등록");
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        channelTwo.setUserMap(userMap);
        channelTwo.setMessageMap(messageMap);
        fileChannelCrudService.create(channelTwo);

        System.out.println(channelTwo.getChannelName() + " 채널 등록");

        try {
            System.out.println(channelOne.getChannelName() + " 채널 읽기");
            System.out.println(fileChannelCrudService.findChannelById(channelOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }

        System.out.println("채널 목록 읽기");
        fileChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");

        try {
            fileChannelCrudService.update(channelTwo.getId(), channelTwo.getChannelName(), channelTwo.getCategory(), false);
            System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }

        try {
            System.out.println(channelTwo.getChannelName() + " 채널 읽기");
            System.out.println(fileChannelCrudService.findChannelById(channelTwo.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }

        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        System.out.println(channelOne.getChannelName() + " 에서 "
                + channelOne.getUserMap().get(user.getId()).getNickname() + " 유저 삭제");
        fileChannelCrudService.deleteUserFromChannel(channelOne.getId(), channelOne.getUserMap().get(user.getId()).getId());
        System.out.println(channelOne.getChannelName() + " 에서 1번째 메시지 삭제");

        fileChannelCrudService.deleteMessageFromChannel(channelOne.getId(), channelOne.getMessageMap().get(message.getId()).getId());
        channelOne.getMessageMap().entrySet().forEach(m -> System.out.println(m.toString()));

        try {
            fileChannelCrudService.deleteById(channelTwo.getId());
            System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }

        System.out.println("채널 목록 읽기");
        fileChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

    }

    public static void testJcfMessageService() {

        UserService jcfUserCrudService = new JCFUserCrudService();
        ChannelService jcfChannelCrudService = new JCFChannelCrudService();
        MessageService jcfMessageCrudService = new JCFMessageCrudService(jcfUserCrudService, jcfChannelCrudService);

        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        jcfUserCrudService.create(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        jcfUserCrudService.create(userTwo);
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        jcfChannelCrudService.create(channelOne);
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        jcfChannelCrudService.create(channelTwo);

        //메시지 등록
        System.out.println("메시지 등록");
        Message messageOne = new Message(userOne.getId(), channelOne.getId(),"messageOne", false, null);
        jcfMessageCrudService.create(messageOne);
        System.out.println(messageOne.getId() + " 메시지 등록");
        Message messageTwo = new Message(userOne.getId(), channelOne.getId(), "messageTwo", true, messageOne.getId());
        jcfMessageCrudService.create(messageTwo);

        System.out.println(messageTwo.getId() + " 메시지 등록");

        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(jcfMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }

        try {
            System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
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
            jcfMessageCrudService.update(messageOne.getId(), "messageOne edited", messageOne.isReply(), messageOne.getParentMessageId());
            System.out.println(messageOne.getId() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }

        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(jcfMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }

        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");

        try {
            jcfMessageCrudService.deleteById(messageTwo.getId());
            System.out.println(messageTwo.getId() + " 메시지 삭제");
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }

        System.out.println("메시지 목록 읽기");
        jcfMessageCrudService.findAllMessages().stream()
                .forEach(message -> System.out.println(message.toString()));

        System.out.println("==========================");

    }

    public static void testFileMessageService() {

        UserService fileUserCrudService = new FileUserCrudService(Path.of(fileDirectory + "users"));
        ChannelService fileChannelCrudService = new FileChannelCrudService(Path.of(fileDirectory + "channels"));
        MessageService fileMessageCrudService = new FileMessageCrudService(Path.of(fileDirectory + "messages"), fileUserCrudService, fileChannelCrudService);

        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        fileUserCrudService.create(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        fileUserCrudService.create(userTwo);
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        fileChannelCrudService.create(channelOne);
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        fileChannelCrudService.create(channelTwo);

        //메시지 등록
        System.out.println("메시지 등록");
        Message messageOne = new Message(userOne.getId(), channelOne.getId(),"messageOne", false, null);
        fileMessageCrudService.create(messageOne);
        System.out.println(messageOne.getId() + " 메시지 등록");
        Message messageTwo = new Message(userOne.getId(), channelOne.getId(), "messageTwo", true, messageOne.getId());
        fileMessageCrudService.create(messageTwo);

        System.out.println(messageTwo.getId() + " 메시지 등록");

        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(fileMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }

        try {
            System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }

        fileMessageCrudService.findChildMessagesById(messageOne.getId()).stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("메시지 목록 읽기");
        fileMessageCrudService.findAllMessages().stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

        //메시지 수정
        System.out.println("메시지 수정");

        try {
            fileMessageCrudService.update(messageOne.getId(), "messageOne edited", messageOne.isReply(), messageOne.getParentMessageId());
            System.out.println(messageOne.getId() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }

        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(fileMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }

        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");
        try {
            fileMessageCrudService.deleteById(messageTwo.getId());
            System.out.println(messageTwo.getId() + " 메시지 삭제");
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }

        System.out.println("메시지 목록 읽기");
        fileMessageCrudService.findAllMessages().stream()
                .forEach(message -> System.out.println(message.toString()));

        System.out.println("==========================");

    }

}
