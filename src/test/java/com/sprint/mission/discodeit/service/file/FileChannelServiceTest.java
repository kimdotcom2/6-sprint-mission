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

    ChannelDTO.CreatePublicChannelRequest channelRequest = ChannelDTO.CreatePublicChannelRequest.builder()
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
        ChannelDTO.CreatePublicChannelRequest channelRequest = ChannelDTO.CreatePublicChannelRequest.builder()
                .channelName("test")
                .category(ChannelType.FORUM)
                .isVoiceChannel(false)
                .build();
        ChannelDTO.CreatePublicChannelRequest channelRequest1 = ChannelDTO.CreatePublicChannelRequest.builder()
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
        ChannelDTO.CreatePublicChannelRequest channelRequest = ChannelDTO.CreatePublicChannelRequest.builder()
                .channelName("test")
                .category(ChannelType.FORUM)
                .isVoiceChannel(false)
                .build();
        fileChannelService.createChannel(channelRequest);

        //when
        ChannelDTO.UpdateChannelRequest updateChannelRequest = ChannelDTO.UpdateChannelRequest.builder()
                .id(fileChannelService.findAllChannels().get(0).id())
                .channelName("test2")
                .category(ChannelType.FORUM)
                .isVoiceChannel(false)
                .build();
        fileChannelService.updateChannel(updateChannelRequest);

        //then
        assertTrue(fileChannelService.existChannelById(updateChannelRequest.id()));
        ChannelDTO.FindChannelResult channel1 = fileChannelService.findChannelById(updateChannelRequest.id()).orElseThrow();
        System.out.println(channel1.toString());
        assertEquals(channel1.channelName(), "test2");
        fileChannelService.findAllChannels().forEach(channel -> fileChannelService.deleteChannelById(channel.id()));

    }

    @Test
    void deleteChannelById() {

        //given
        ChannelDTO.CreatePublicChannelRequest channelRequest = ChannelDTO.CreatePublicChannelRequest.builder()
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