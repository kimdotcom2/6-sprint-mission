package com.sprint.mission.discodit;

import com.sprint.mission.discodit.entity.Channel;
import com.sprint.mission.discodit.entity.Message;
import com.sprint.mission.discodit.entity.User;
import com.sprint.mission.discodit.service.JCFChannelService;
import com.sprint.mission.discodit.service.JCFMessageService;
import com.sprint.mission.discodit.service.JCFUserService;
import com.sprint.mission.discodit.service.jcf.JCFChannelCrudService;
import com.sprint.mission.discodit.service.jcf.JCFMessageCrudService;
import com.sprint.mission.discodit.service.jcf.JCFUserCrudService;

public class JavaApplication {

    public static void main(String[] args) {

        JCFUserService userService = new JCFUserCrudService();
        JCFChannelService channelService = new JCFChannelCrudService();
        JCFMessageService messageService = new JCFMessageCrudService(userService, channelService);

        //유저 등록
        User userOne = new User("Kim", "kimjaewon@gmail.com", "1234", "Hi");
        userService.create(userOne);
        User userTwo = new User("Kim2", "kimjaewon2@gmail.com", "1234", "Hi");
        userService.create(userTwo);
        userService.readById(userOne.getId().toString());
        userService.readAll();
        System.out.println("==========================");

        //유저 수정
        userService.update(userOne.getId().toString(), userOne.getNickname(), userOne.getEmail(), userOne.getPassword(), "Bye");
        userService.readById(userOne.getId().toString());
        System.out.println("==========================");

        //유저 삭제
        userService.deleteById(userTwo.getId().toString());
        userService.readById(userTwo.getId().toString());
        userService.readAll();
        System.out.println("==========================");

        //채널 등록
        Channel channelOne = new Channel("channelOne", "channelOne", false);
        channelService.create(channelOne);
        Channel channelTwo = new Channel("channelTwo", "channelTwo", true);
        channelService.create(channelTwo);
        channelService.readById(channelOne.getId().toString());
        channelService.readAll();
        System.out.println("==========================");

        //채널 수정
        System.out.println("channelOne before: " + channelOne.toString());
        channelService.update(channelTwo.getId().toString(), channelTwo.getChannelName(), channelTwo.getCategory(), false);
        channelService.readById(channelTwo.getId().toString());
        System.out.println("==========================");

        //채널 삭제
        channelService.deleteById(channelTwo.getId().toString());
        channelService.readById(channelTwo.getId().toString());
        channelService.readAll();
        System.out.println("==========================");

        //메시지 등록
        Message messageOne = new Message(userOne.getId(), channelOne.getId(),"messageOne", false, null);
        messageService.create(messageOne);
        Message messageTwo = new Message(userTwo.getId(), channelTwo.getId(), "messageTwo", true, messageOne.getId());
        messageService.create(messageTwo);
        messageService.readById(messageOne.getId().toString());
        messageService.readAll();
        System.out.println("==========================");

        //메시지 수정
        messageService.update(messageOne.getId().toString(), "messageOne edited", messageOne.isReply(), messageOne.getParentMessageId());
        messageService.readById(messageOne.getId().toString());
        System.out.println("==========================");

        //메시지 삭제
        messageService.deleteById(messageTwo.getId().toString());
        messageService.readById(messageTwo.getId().toString());
        messageService.readAll();
        System.out.println("==========================");


    }

}
