package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.dto.api.ChannelApiDTO;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/public")
    public ResponseEntity<ChannelApiDTO.FindChannelResponse> createPublicChannel(@RequestBody ChannelApiDTO.PublicChannelCreateRequest publicChannelCreateRequest) {

        ChannelDTO.CreatePublicChannelCommand createPublicChannelCommand = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName(publicChannelCreateRequest.channelName())
                .category(ChannelType.valueOf(publicChannelCreateRequest.category()))
                .isVoiceChannel(publicChannelCreateRequest.isVoiceChannel())
                .description(publicChannelCreateRequest.description())
                .build();

        channelService.createChannel(createPublicChannelCommand);

        ChannelDTO.FindChannelResult findChannelResult = channelService.findAllChannels().stream()
            .filter(
                channel -> channel.channelName().equals(publicChannelCreateRequest.channelName()))
            .min((channel1, channel2) -> channel2.createdAt().compareTo(channel1.createdAt()))
                .orElseThrow(() -> new NoSuchElementException("No such channels"));

        return ResponseEntity.status(201).body(ChannelApiDTO.FindChannelResponse.builder()
                .id(findChannelResult.id())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(findChannelResult.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(findChannelResult.updatedAt()), ZoneId.systemDefault()))
                .category(findChannelResult.category())
                .isVoiceChannel(findChannelResult.isVoiceChannel())
                .type("PUBLIC")
                .channelName(findChannelResult.channelName())
                .description(findChannelResult.description())
                .userIdList(new ArrayList<>())
                .build());

    }

    @PostMapping("/private")
    public ResponseEntity<ChannelApiDTO.FindChannelResponse> createPrivateChannel(@RequestBody ChannelApiDTO.CreatePrivateChannelRequest createPrivateChannelRequest) {

        ChannelDTO.CreatePrivateChannelCommand createPrivateChannelCommand = ChannelDTO.CreatePrivateChannelCommand.builder()
                .category(createPrivateChannelRequest.category())
                .isVoiceChannel(createPrivateChannelRequest.isVoiceChannel())
                .userIdList(createPrivateChannelRequest.userIdList())
                .description(createPrivateChannelRequest.description())
                .build();

        channelService.createPrivateChannel(createPrivateChannelCommand);

        ChannelDTO.FindChannelResult findChannelResult = channelService.findAllChannels().stream()
            .min((channel1, channel2) -> channel2.createdAt().compareTo(channel1.createdAt()))
                .orElseThrow(() -> new NoSuchElementException("No such channels"));

        return ResponseEntity.status(201).body(ChannelApiDTO.FindChannelResponse.builder()
                .id(findChannelResult.id())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(findChannelResult.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(findChannelResult.updatedAt()), ZoneId.systemDefault()))
                .category(findChannelResult.category())
                .isVoiceChannel(findChannelResult.isVoiceChannel())
                .type("PRIVATE")
                .channelName(findChannelResult.channelName())
                .description(findChannelResult.description())
                .userIdList(new ArrayList<>())
                .build());

    }

    @RequestMapping(value = "/api/channel/public-channel", method = RequestMethod.PUT)
    public ResponseEntity<String> updatePublicChannel(@RequestBody ChannelApiDTO.UpdateChannelRequest updateChannelRequest) {

        ChannelDTO.UpdateChannelCommand updateChannelCommand = ChannelDTO.UpdateChannelCommand.builder()
                .id(updateChannelRequest.id())
                .channelName(updateChannelRequest.channelName())
                .category(ChannelType.valueOf(updateChannelRequest.category()))
                .isVoiceChannel(updateChannelRequest.isVoiceChannel())
                .description(updateChannelRequest.description())
                .build();

        channelService.updateChannel(updateChannelCommand);

        return ResponseEntity.ok("Public channel updated successfully");

    }

    @RequestMapping(value = "/api/channel/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteChannel(@RequestBody ChannelApiDTO.DeleteChannelRequest deleteChannelRequest) {

        channelService.deleteChannelById(deleteChannelRequest.id());

        return ResponseEntity.ok("Channel deleted successfully");

    }

    @RequestMapping(value = "/api/user/{userId}/channels", method = RequestMethod.GET)
    public List<ChannelApiDTO.FindChannelResponse> findChannelsByUserId(@PathVariable UUID userId) {

        return channelService.findChannelsByUserId(userId).stream()
                .map(channel -> ChannelApiDTO.FindChannelResponse.builder()
                        .id(channel.id())
                        .channelName(channel.channelName())
                        .category(channel.category())
                        .isVoiceChannel(channel.isVoiceChannel())
                        .description(channel.description())
                        .build())
                .toList();

    }

}
