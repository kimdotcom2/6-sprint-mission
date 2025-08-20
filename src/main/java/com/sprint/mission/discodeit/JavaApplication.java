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
import java.util.*;

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
        jcfUserCrudService.createUser(userOne);
        System.out.println(userOne.getNickname() + " 유저 추가");
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        jcfUserCrudService.createUser(userTwo);
        System.out.println(userTwo.getNickname() + " 유저 추가");

        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(jcfUserCrudService.findUserById(userOne.getId())
                    .orElseThrow((IllegalArgumentException::new)).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No user found with id: " + userOne.getId() + "");
        }

        System.out.println("유저 목록 읽기");
        jcfUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");

        try {
            jcfUserCrudService.updateUser(userOne.getId(), userOne.getNickname(), userOne.getEmail(), userOne.getPassword(), "Bye");
            System.out.println(userOne.getNickname() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(jcfUserCrudService.findUserById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No user found with id: " + userOne.getId() + "");
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

        UserService fileUserCrudService = new FileUserCrudService(Path.of(fileDirectory + "users"));

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        fileUserCrudService.createUser(userOne);
        System.out.println(userOne.getNickname() + " 유저 추가");
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        fileUserCrudService.createUser(userTwo);
        System.out.println(userTwo.getNickname() + " 유저 추가");

        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(fileUserCrudService.findUserById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No user found with id: " + userOne.getId() + "");
        }

        System.out.println("유저 목록 읽기");
        fileUserCrudService.findAllUsers().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");

        try {
            fileUserCrudService.updateUser(userOne.getId(), userOne.getNickname(), userOne.getEmail(), userOne.getPassword(), "Bye");
            System.out.println(userOne.getNickname() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(fileUserCrudService.findUserById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No user found with id: " + userOne.getId() + "");
        }

        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");

        try {
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

    public static void testJcfChannelService() {

        ChannelService jcfChannelCrudService = new JCFChannelCrudService();

        //채널 등록
        User user = new User("test", "test", "test", "test");
        Message message = new Message(user.getId(), null, "message", false, null);

        System.out.println("채널 등록");
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        jcfChannelCrudService.addUserToChannel(channelOne.getId(), user);
        jcfChannelCrudService.addMessageToChannel(channelOne.getId(), message);
        jcfChannelCrudService.createChannel(channelOne);
        System.out.println(channelOne.getChannelName() + " 채널 등록");
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        jcfChannelCrudService.addUserToChannel(channelTwo.getId(), user);
        jcfChannelCrudService.addMessageToChannel(channelTwo.getId(), message);
        jcfChannelCrudService.createChannel(channelTwo);

        System.out.println(channelTwo.getChannelName() + " 채널 등록");
        try {
            System.out.println(channelOne.getChannelName() + " 채널 읽기");
            System.out.println(jcfChannelCrudService.findChannelById(channelOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No channel found with id: " + channelOne.getId() + "");
        }

        System.out.println("채널 목록 읽기");
        jcfChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");

        try {
            jcfChannelCrudService.updatechannel(channelTwo.getId(), channelTwo.getChannelName(), channelTwo.getCategory(), false);
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
        channelOne.getMessageMap().entrySet().forEach(m -> System.out.println(m.toString()));

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

        ChannelService fileChannelCrudService = new FileChannelCrudService(Path.of(fileDirectory + "channels"));

        //채널 등록
        User user = new User("test", "test", "test", "test");
        Message message = new Message(user.getId(), null, "message", false, null);

        System.out.println("채널 등록");
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        fileChannelCrudService.addUserToChannel(channelOne.getId(), user);
        fileChannelCrudService.addMessageToChannel(channelOne.getId(), message);
        fileChannelCrudService.createChannel(channelOne);
        System.out.println(channelOne.getChannelName() + " 채널 등록");
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        fileChannelCrudService.addUserToChannel(channelTwo.getId(), user);
        fileChannelCrudService.addMessageToChannel(channelTwo.getId(), message);
        fileChannelCrudService.createChannel(channelTwo);

        System.out.println(channelTwo.getChannelName() + " 채널 등록");

        try {
            System.out.println(channelOne.getChannelName() + " 채널 읽기");
            System.out.println(fileChannelCrudService.findChannelById(channelOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No channel found with id: " + channelOne.getId() + "");
        }

        System.out.println("채널 목록 읽기");
        fileChannelCrudService.findAllChannels().stream()
                .forEach(channel -> System.out.println(channel.toString()));

        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");

        try {
            fileChannelCrudService.updatechannel(channelTwo.getId(), channelTwo.getChannelName(), channelTwo.getCategory(), false);
            System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        try {
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
        fileChannelCrudService.deleteUserFromChannel(channelOne.getId(), channelOne.getUserMap().get(user.getId()).getId());
        System.out.println(channelOne.getChannelName() + " 에서 1번째 메시지 삭제");

        fileChannelCrudService.deleteMessageFromChannel(channelOne.getId(), channelOne.getMessageMap().get(message.getId()).getId());
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

    public static void testJcfMessageService() {

        UserService jcfUserCrudService = new JCFUserCrudService();
        ChannelService jcfChannelCrudService = new JCFChannelCrudService();
        MessageService jcfMessageCrudService = new JCFMessageCrudService(jcfUserCrudService, jcfChannelCrudService);

        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        jcfUserCrudService.createUser(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        jcfUserCrudService.createUser(userTwo);
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        jcfChannelCrudService.createChannel(channelOne);
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        jcfChannelCrudService.createChannel(channelTwo);

        //메시지 등록
        System.out.println("메시지 등록");
        Message messageOne = new Message(userOne.getId(), channelOne.getId(),"messageOne", false, null);
        jcfMessageCrudService.createMessage(messageOne);
        System.out.println(messageOne.getId() + " 메시지 등록");
        Message messageTwo = new Message(userOne.getId(), channelOne.getId(), "messageTwo", true, messageOne.getId());
        jcfMessageCrudService.createMessage(messageTwo);

        System.out.println(messageTwo.getId() + " 메시지 등록");

        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(jcfMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No message found with id: " + messageOne.getId() + "");
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
            jcfMessageCrudService.updateMessage(messageOne.getId(), "messageOne edited", messageOne.isReply(), messageOne.getParentMessageId());
            System.out.println(messageOne.getId() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(jcfMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No message found with id: " + messageOne.getId() + "");
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

        UserService fileUserCrudService = new FileUserCrudService(Path.of(fileDirectory + "users"));
        ChannelService fileChannelCrudService = new FileChannelCrudService(Path.of(fileDirectory + "channels"));
        MessageService fileMessageCrudService = new FileMessageCrudService(Path.of(fileDirectory + "messages"), fileUserCrudService, fileChannelCrudService);

        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        fileUserCrudService.createUser(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        fileUserCrudService.createUser(userTwo);
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        fileChannelCrudService.createChannel(channelOne);
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        fileChannelCrudService.createChannel(channelTwo);

        //메시지 등록
        System.out.println("메시지 등록");
        Message messageOne = new Message(userOne.getId(), channelOne.getId(),"messageOne", false, null);
        fileMessageCrudService.createMessage(messageOne);
        System.out.println(messageOne.getId() + " 메시지 등록");
        Message messageTwo = new Message(userOne.getId(), channelOne.getId(), "messageTwo", true, messageOne.getId());
        fileMessageCrudService.createMessage(messageTwo);

        System.out.println(messageTwo.getId() + " 메시지 등록");

        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(fileMessageCrudService.findMessageById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No message found with id: " + messageOne.getId() + "");
        }

        try {
            System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
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
            fileMessageCrudService.updateMessage(messageOne.getId(), "messageOne edited", messageOne.isReply(), messageOne.getParentMessageId());
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

}
