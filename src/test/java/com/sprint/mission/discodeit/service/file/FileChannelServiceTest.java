package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileChannelServiceTest {

    ChannelService fileChannelService = new FileChannelService(Path.of("C:\\spring\\codeit-bootcamp-spring\\6-sprint-mission\\src\\main\\resources\\data\\channels"));

    ChannelDTO.CreatePublicChannelCommand channelRequest = ChannelDTO.CreatePublicChannelCommand.builder()
            .channelName("test")
            .category(ChannelType.FORUM)
            .isVoiceChannel(false)
            .build();

    @Test
    void createChannel() {

        //given
        Channel channel = new Channel("test", ChannelType.FORUM, true, false);

        //when
        fileChannelService.createChannel(channelRequest);

        //then
        assertTrue(fileChannelService.existChannelById(channel.getId()));
        fileChannelService.deleteChannelById(channel.getId());

    }

    @Test
    void existChannelById() {

        //given
        fileChannelService.createChannel(channelRequest);

        //when
        ChannelDTO.FindChannelResult channel = fileChannelService.findAllChannels().stream()
                .filter(c -> c.channelName().equals("test"))
                .findFirst()
                .orElse(null);

        //then
        assertTrue(fileChannelService.existChannelById(channel.id()));
        fileChannelService.deleteChannelById(channel.id());

    }

    @Test
    void findChannelById() {

        //given
        fileChannelService.createChannel(channelRequest);

        //when
        ChannelDTO.FindChannelResult channel = fileChannelService.findAllChannels().stream()
                .filter(c -> c.channelName().equals("test"))
                .findFirst()
                .orElse(null);

        //then
        assertTrue(fileChannelService.existChannelById(channel.id()));
        System.out.println(channel.toString());
        assertEquals(channel.id(), channel.id());
        fileChannelService.deleteChannelById(channel.id());

    }

    @Test
    void findAllChannels() {

        //given
        ChannelDTO.CreatePublicChannelCommand channelRequest = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName("test")
                .category(ChannelType.FORUM)
                .isVoiceChannel(false)
                .build();
        ChannelDTO.CreatePublicChannelCommand channelRequest1 = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName("test1")
                .category(ChannelType.VOICE)
                .isVoiceChannel(true).build();

        //when
        fileChannelService.createChannel(channelRequest);
        fileChannelService.createChannel(channelRequest1);

        //then
        fileChannelService.findAllChannels().forEach(System.out::println);
        fileChannelService.findAllChannels().forEach(channel -> fileChannelService.deleteChannelById(channel.id()));

    }

    @Test
    void updateChannel() {

        //given
        ChannelDTO.CreatePublicChannelCommand channelRequest = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName("test")
                .category(ChannelType.FORUM)
                .isVoiceChannel(false)
                .build();
        fileChannelService.createChannel(channelRequest);

        //when
        ChannelDTO.UpdateChannelCommand updateChannelCommand = ChannelDTO.UpdateChannelCommand.builder()
                .id(fileChannelService.findAllChannels().get(0).id())
                .channelName("test2")
                .category(ChannelType.FORUM)
                .isVoiceChannel(false)
                .build();
        fileChannelService.updateChannel(updateChannelCommand);

        //then
        assertTrue(fileChannelService.existChannelById(updateChannelCommand.id()));
        ChannelDTO.FindChannelResult channel1 = fileChannelService.findChannelById(updateChannelCommand.id()).orElseThrow();
        System.out.println(channel1.toString());
        assertEquals(channel1.channelName(), "test2");
        fileChannelService.findAllChannels().forEach(channel -> fileChannelService.deleteChannelById(channel.id()));

    }

    @Test
    void deleteChannelById() {

        //given
        ChannelDTO.CreatePublicChannelCommand channelRequest = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName("test")
                .category(ChannelType.FORUM)
                .isVoiceChannel(false)
                .build();
        fileChannelService.createChannel(channelRequest);
        assertTrue(fileChannelService.existChannelById(fileChannelService.findAllChannels().get(0).id()));

        //when
        fileChannelService.deleteChannelById(fileChannelService.findAllChannels().get(0).id());

        //then
        assertFalse(fileChannelService.existChannelById(fileChannelService.findAllChannels().get(0).id()));

    }
}