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

        JCFUserService userService = new JCFUserCrudService();
        JCFChannelService channelService = new JCFChannelCrudService();
        JCFMessageService messageService = new JCFMessageCrudService(userService, channelService);

        //유저 등록
        System.out.println("유저 등록");
        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        userService.create(userOne);
        System.out.println(userOne.getNickname() + " 유저 추가");
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        userService.create(userTwo);
        System.out.println(userTwo.getNickname() + " 유저 추가");
        System.out.println(userOne.getNickname() + " 유저 읽기");
        userService.readById(userOne.getId().toString());
        System.out.println("유저 목록 읽기");
        userService.readAll();
        System.out.println("==========================");

        //유저 수정
        System.out.println("유저 수정");
        userService.update(userOne.getId().toString(), userOne.getNickname(), userOne.getEmail(), userOne.getPassword(), "Bye");
        System.out.println(userOne.getNickname() + " 정보 업데이트");
        System.out.println(userOne.getNickname() + " 유저 읽기");
        userService.readById(userOne.getId().toString());
        System.out.println("==========================");

        //유저 삭제
        System.out.println("유저 삭제");
        userService.deleteById(userTwo.getId().toString());
        System.out.println(userTwo.getNickname() + " 유저 삭제");
        System.out.println(userTwo.getNickname() + " 유저 읽기");
        userService.readById(userTwo.getId().toString());
        System.out.println("유저 목록 읽기");
        userService.readAll();
        System.out.println("==========================");

        //채널 등록
        System.out.println("채널 등록");
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        channelService.create(channelOne);
        System.out.println(channelOne.getChannelName() + " 채널 등록");
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        channelService.create(channelTwo);
        System.out.println(channelTwo.getChannelName() + " 채널 등록");
        System.out.println(channelOne.getChannelName() + " 채널 읽기");
        channelService.readById(channelOne.getId().toString());
        System.out.println("채널 목록 읽기");
        channelService.readAll();
        System.out.println("==========================");

        //채널 수정
        System.out.println("채널 수정");
        channelService.update(channelTwo.getId().toString(), channelTwo.getChannelName(), channelTwo.getCategory(), false);
        System.out.println(channelTwo.getChannelName() + " 정보 업데이트");
        System.out.println(channelTwo.getChannelName() + " 채널 읽기");
        channelService.readById(channelTwo.getId().toString());
        System.out.println("==========================");

        //채널 삭제
        System.out.println("채널 삭제");
        channelService.deleteById(channelTwo.getId().toString());
        System.out.println(channelTwo.getChannelName() + " 채널 삭제");
        System.out.println(channelTwo.getChannelName() + " 채널 읽기");
        channelService.readById(channelTwo.getId().toString());
        System.out.println("채널 목록 읽기");
        channelService.readAll();
        System.out.println("==========================");

        //메시지 등록
        System.out.println("메시지 등록");
        Message messageOne = new Message(userOne.getId(), channelOne.getId(),"messageOne", false, null);
        messageService.create(messageOne);
        System.out.println(messageOne.getId() + " 메시지 등록");
        Message messageTwo = new Message(userTwo.getId(), channelTwo.getId(), "messageTwo", true, messageOne.getId());
        messageService.create(messageTwo);
        System.out.println(messageTwo.getId() + " 메시지 등록");
        System.out.println(messageOne.getId() + " 메시지 읽기");
        messageService.readById(messageOne.getId().toString());
        System.out.println("메시지 목록 읽기");
        messageService.readAll();
        System.out.println("==========================");

        //메시지 수정
        System.out.println("메시지 수정");
        messageService.update(messageOne.getId().toString(), "messageOne edited", messageOne.isReply(), messageOne.getParentMessageId());
        System.out.println(messageOne.getId() + " 정보 업데이트");
        System.out.println(messageOne.getId() + " 메시지 읽기");
        messageService.readById(messageOne.getId().toString());
        System.out.println("==========================");

        //메시지 삭제
        System.out.println("메시지 삭제");
        messageService.deleteById(messageTwo.getId().toString());
        System.out.println(messageTwo.getId() + " 메시지 삭제");
        System.out.println(messageTwo.getId() + " 메시지 읽기");
        messageService.readById(messageTwo.getId().toString());
        System.out.println("메시지 목록 읽기");
        messageService.readAll();
        System.out.println("==========================");


    }

}
