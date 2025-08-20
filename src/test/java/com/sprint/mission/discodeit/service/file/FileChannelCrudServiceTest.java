package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileChannelCrudServiceTest {

    ChannelService fileChannelService = new FileChannelCrudService(Path.of("C:\\spring\\codeit-bootcamp-spring\\6-sprint-mission\\src\\main\\resources\\data\\channels"));

    @Test
    void create() {

        //given
        Channel channel = new Channel("test", "test", true);

        //when
        fileChannelService.create(channel);

        //then
        assertTrue(fileChannelService.existById(channel.getId()));
        fileChannelService.deleteById(channel.getId());

    }

    @Test
    void existById() {

        //given
        Channel channel = new Channel("test", "test", true);

        //when
        fileChannelService.create(channel);

        //then
        assertTrue(fileChannelService.existById(channel.getId()));
        fileChannelService.deleteById(channel.getId());

    }

    @Test
    void readById() {

        //given
        Channel channel = new Channel("test", "test", true);

        //when
        fileChannelService.create(channel);

        //then
        assertTrue(fileChannelService.existById(channel.getId()));
        Channel channel1 = fileChannelService.readById(channel.getId()).orElse(null);
        System.out.println(channel1.toString());
        assertEquals(channel1.getId(), channel.getId());
        fileChannelService.deleteById(channel.getId());

    }

    @Test
    void readAll() {

        //given
        Channel channel = new Channel("test", "test", true);
        Channel channel1 = new Channel("test1", "test1", false);

        //when
        fileChannelService.create(channel);
        fileChannelService.create(channel1);

        //then
        fileChannelService.readAll().forEach(System.out::println);
        fileChannelService.deleteById(channel.getId());
        fileChannelService.deleteById(channel1.getId());

    }

    @Test
    void update() {

        //given
        Channel channel = new Channel("test", "test", true);
        fileChannelService.create(channel);

        //when
        fileChannelService.update(channel.getId(), "test2", "test2", false);

        //then
        assertTrue(fileChannelService.existById(channel.getId()));
        Channel channel1 = fileChannelService.readById(channel.getId()).orElse(null);
        System.out.println(channel1.toString());
        assertEquals(channel1.getChannelName(), "test2");
        fileChannelService.deleteById(channel.getId());

    }

    @Test
    void deleteById() {

        //given
        Channel channel = new Channel("test", "test", true);
        fileChannelService.create(channel);
        assertTrue(fileChannelService.existById(channel.getId()));

        //when
        fileChannelService.deleteById(channel.getId());

        //then
        assertFalse(fileChannelService.existById(channel.getId()));

    }
}