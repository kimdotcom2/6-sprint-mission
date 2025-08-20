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
            System.out.println(jcfUserCrudService.readById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }
        System.out.println("유저 목록 읽기");
        jcfUserCrudService.readAll().stream()
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
            System.out.println(jcfUserCrudService.readById(userOne.getId())
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
        jcfUserCrudService.readAll().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

    }

    public static void testFileUserService() {

        UserService fileUserService = new FileUserCrudService(Path.of(fileDirectory + "users"));

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        fileUserService.create(userOne);
        System.out.println(userOne.getNickname() + " 유저 추가");
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        fileUserService.create(userTwo);
        System.out.println(userTwo.getNickname() + " 유저 추가");
        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(fileUserService.readById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }
        System.out.println("유저 목록 읽기");
        fileUserService.readAll().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");
        try {
            fileUserService.update(userOne.getId(), userOne.getNickname(), userOne.getEmail(), userOne.getPassword(), "Bye");
            System.out.println(userOne.getNickname() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }
        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(fileUserService.readById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }
        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        try {
            fileUserService.deleteById(userTwo.getId());
            System.out.println(userTwo.getNickname() + " 유저 삭제");
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }
        System.out.println("유저 목록 읽기");
        fileUserService.readAll().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

    }

    public static void testJcfChannelService() {

        ChannelService jcfChannelCrudService = new JCFChannelCrudService();

        //채널 등록
        System.out.println("채널 등록");
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        jcfChannelCrudService.create(channelOne);
        System.out.println(channelOne.getChannelName() + " 채널 등록");
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        jcfChannelCrudService.create(channelTwo);
        System.out.println(channelTwo.getChannelName() + " 채널 등록");
        try {
            System.out.println(channelOne.getChannelName() + " 채널 읽기");
            System.out.println(jcfChannelCrudService.readById(channelOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }
        System.out.println("채널 목록 읽기");
        jcfChannelCrudService.readAll().stream()
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
            System.out.println(jcfChannelCrudService.readById(channelTwo.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        try {
            jcfChannelCrudService.deleteById(channelTwo.getId());
            System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }
        System.out.println("채널 목록 읽기");
        jcfChannelCrudService.readAll().stream()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

    }

    public static void testFileChannelService() {

        ChannelService fileChannelService = new FileChannelCrudService(Path.of(fileDirectory + "channels"));

        //채널 등록
        System.out.println("채널 등록");
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        fileChannelService.create(channelOne);
        System.out.println(channelOne.getChannelName() + " 채널 등록");
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        fileChannelService.create(channelTwo);
        System.out.println(channelTwo.getChannelName() + " 채널 등록");
        try {
            System.out.println(channelOne.getChannelName() + " 채널 읽기");
            System.out.println(fileChannelService.readById(channelOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }
        System.out.println("채널 목록 읽기");
        fileChannelService.readAll().stream()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");
        try {
            fileChannelService.update(channelTwo.getId(), channelTwo.getChannelName(), channelTwo.getCategory(), false);
            System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }
        try {
            System.out.println(channelTwo.getChannelName() + " 채널 읽기");
            System.out.println(fileChannelService.readById(channelTwo.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        try {
            fileChannelService.deleteById(channelTwo.getId());
            System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }
        System.out.println("채널 목록 읽기");
        fileChannelService.readAll().stream()
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
            System.out.println(jcfMessageCrudService.readById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }
        try {
            System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }
        jcfMessageCrudService.readChildrenById(messageOne.getId()).stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("메시지 목록 읽기");
        jcfMessageCrudService.readAll().stream()
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
            System.out.println(jcfMessageCrudService.readById(messageOne.getId())
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
        jcfMessageCrudService.readAll().stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

    }

    public static void testFileMessageService() {

        UserService fileUserService = new FileUserCrudService(Path.of(fileDirectory + "users"));
        ChannelService fileChannelService = new FileChannelCrudService(Path.of(fileDirectory + "channels"));
        MessageService fileMessageCrudService = new FileMessageCrudService(Path.of(fileDirectory + "messages"), fileUserService, fileChannelService);

        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        fileUserService.create(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        fileUserService.create(userTwo);
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        fileChannelService.create(channelOne);
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        fileChannelService.create(channelTwo);

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
            System.out.println(fileMessageCrudService.readById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }
        try {
            System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }
        fileMessageCrudService.readChildrenById(messageOne.getId()).stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("메시지 목록 읽기");
        fileMessageCrudService.readAll().stream()
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
            System.out.println(fileMessageCrudService.readById(messageOne.getId())
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
        fileMessageCrudService.readAll().stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

    }

}
