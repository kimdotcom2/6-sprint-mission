package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.JCFChannelService;
import com.sprint.mission.discodeit.service.JCFMessageService;
import com.sprint.mission.discodeit.service.JCFUserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelCrudService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageCrudService;
import com.sprint.mission.discodeit.service.jcf.JCFUserCrudService;

public class JavaApplication {

    public static void main(String[] args) {

        testUserService();
        testChannelService();
        testMessageService();

    }

    public static void testUserService() {

        JCFUserService userService = new JCFUserCrudService();

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        userService.create(userOne);
        System.out.println(userOne.getNickname() + " 유저 추가");
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        userService.create(userTwo);
        System.out.println(userTwo.getNickname() + " 유저 추가");
        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(userService.readById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }
        System.out.println("유저 목록 읽기");
        userService.readAll().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");
        userService.update(userOne.getId(), userOne.getNickname(), userOne.getEmail(), userOne.getPassword(), "Bye");
        System.out.println(userOne.getNickname() + " 정보 업데이트");
        try {
            System.out.println(userOne.getNickname() + " 유저 읽기");
            System.out.println(userService.readById(userOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such user.");
        }
        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        userService.deleteById(userTwo.getId());
        System.out.println(userTwo.getNickname() + " 유저 삭제");
        System.out.println("유저 목록 읽기");
        userService.readAll().stream()
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================");

    }

    public static void testChannelService() {

        JCFChannelService channelService = new JCFChannelCrudService();

        //채널 등록
        System.out.println("채널 등록");
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        channelService.create(channelOne);
        System.out.println(channelOne.getChannelName() + " 채널 등록");
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        channelService.create(channelTwo);
        System.out.println(channelTwo.getChannelName() + " 채널 등록");
        try {
            System.out.println(channelOne.getChannelName() + " 채널 읽기");
            System.out.println(channelService.readById(channelOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }
        System.out.println("채널 목록 읽기");
        channelService.readAll().stream()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");
        channelService.update(channelTwo.getId(), channelTwo.getChannelName(), channelTwo.getCategory(), false);
        System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        try {
            System.out.println(channelTwo.getChannelName() + " 채널 읽기");
            System.out.println(channelService.readById(channelTwo.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such channel.");
        }
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        channelService.deleteById(channelTwo.getId());
        System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        System.out.println("채널 목록 읽기");
        channelService.readAll().stream()
                .forEach(channel -> System.out.println(channel.toString()));
        System.out.println("==========================");

    }

    public static void testMessageService() {

        JCFUserService userService = new JCFUserCrudService();
        JCFChannelService channelService = new JCFChannelCrudService();
        JCFMessageService messageService = new JCFMessageCrudService(userService, channelService);

        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        userService.create(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        userService.create(userTwo);
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        channelService.create(channelOne);
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        channelService.create(channelTwo);

        //메시지 등록
        System.out.println("메시지 등록");
        Message messageOne = new Message(userOne.getId(), channelOne.getId(),"messageOne", false, null);
        messageService.create(messageOne);
        System.out.println(messageOne.getId() + " 메시지 등록");
        Message messageTwo = new Message(userOne.getId(), channelOne.getId(), "messageTwo", true, messageOne.getId());
        messageService.create(messageTwo);
        System.out.println(messageTwo.getId() + " 메시지 등록");
        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(messageService.readById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }
        try {
            System.out.println(messageOne.getId() + " 의 답글 메시지 읽기");
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }
        messageService.readChildrenById(messageOne.getId()).stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("메시지 목록 읽기");
        messageService.readAll().stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

        //메시지 수정
        System.out.println("메시지 수정");
        messageService.update(messageOne.getId(), "messageOne edited", messageOne.isReply(), messageOne.getParentMessageId());
        System.out.println(messageOne.getId() + " 정보 업데이트");
        try {
            System.out.println(messageOne.getId() + " 메시지 읽기");
            System.out.println(messageService.readById(messageOne.getId())
                    .orElseThrow(IllegalArgumentException::new).toString());
        } catch (IllegalArgumentException e) {
            System.out.println("No such message.");
        }
        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");
        messageService.deleteById(messageTwo.getId());
        System.out.println(messageTwo.getId() + " 메시지 삭제");
        System.out.println("메시지 목록 읽기");
        messageService.readAll().stream()
                .forEach(message -> System.out.println(message.toString()));
        System.out.println("==========================");

    }

}
