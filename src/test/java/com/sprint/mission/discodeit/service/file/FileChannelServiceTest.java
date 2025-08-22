package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.dto.DiscordDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileChannelServiceTest {

    ChannelService fileChannelService = new FileChannelService(Path.of("C:\\spring\\codeit-bootcamp-spring\\6-sprint-mission\\src\\main\\resources\\data\\channels"));

    @Test
    void createChannel() {

        //given
        Channel channel = new Channel("test", ChannelType.FORUM, true);

        //when
        fileChannelService.createChannel(channel);

        //then
        assertTrue(fileChannelService.existChannelById(channel.getId()));
        fileChannelService.deleteChannelById(channel.getId());

    }

    @Test
    void existChannelById() {

        //given
        Channel channel = new Channel("test", ChannelType.FORUM, true);

        //when
        fileChannelService.createChannel(channel);

        //then
        assertTrue(fileChannelService.existChannelById(channel.getId()));
        fileChannelService.deleteChannelById(channel.getId());

    }

    @Test
    void findChannelById() {

        //given
        Channel channel = new Channel("test", ChannelType.FORUM, true);

        //when
        fileChannelService.createChannel(channel);

        //then
        assertTrue(fileChannelService.existChannelById(channel.getId()));
        Channel channel1 = fileChannelService.findChannelById(channel.getId()).orElse(null);
        System.out.println(channel1.toString());
        assertEquals(channel1.getId(), channel.getId());
        fileChannelService.deleteChannelById(channel.getId());

    }

    @Test
    void findAllChannels() {

        //given
        Channel channel = new Channel("test", ChannelType.FORUM, true);
        Channel channel1 = new Channel("test1", ChannelType.VOICE, false);

        //when
        fileChannelService.createChannel(channel);
        fileChannelService.createChannel(channel1);

        //then
        fileChannelService.findAllChannels().forEach(System.out::println);
        fileChannelService.deleteChannelById(channel.getId());
        fileChannelService.deleteChannelById(channel1.getId());

    }

    @Test
    void updateChannel() {

        //given
        Channel channel = new Channel("test", ChannelType.VOICE, true);
        fileChannelService.createChannel(channel);

        //when
        DiscordDTO.UpdateChannelRequest channelRequest = new DiscordDTO.UpdateChannelRequest.Builder()
                .id(channel.getId())
                .channelName("test2")
                .category(ChannelType.FORUM)
                .isVoiceChannel(false)
                .build();
        fileChannelService.updateChannel(channelRequest);

        //then
        assertTrue(fileChannelService.existChannelById(channel.getId()));
        Channel channel1 = fileChannelService.findChannelById(channel.getId()).orElse(null);
        System.out.println(channel1.toString());
        assertEquals(channel1.getChannelName(), "test2");
        fileChannelService.deleteChannelById(channel.getId());

    }

    @Test
    void deleteChannelById() {

        //given
        Channel channel = new Channel("test", ChannelType.TEXT, true);
        fileChannelService.createChannel(channel);
        assertTrue(fileChannelService.existChannelById(channel.getId()));

        //when
        fileChannelService.deleteChannelById(channel.getId());

        //then
        assertFalse(fileChannelService.existChannelById(channel.getId()));

    }
}