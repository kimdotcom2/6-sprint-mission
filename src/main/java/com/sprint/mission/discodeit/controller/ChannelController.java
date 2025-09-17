package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.dto.api.ChannelApiDTO;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @RequestMapping(value = "/api/channel/public-channel", method = RequestMethod.POST)
    public ResponseEntity<String> createPublicChannel(@RequestBody ChannelApiDTO.CreatePublicChannelRequest createPublicChannelRequest) {

        ChannelDTO.CreatePublicChannelCommand createPublicChannelCommand = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName(createPublicChannelRequest.channelName())
                .category(ChannelType.valueOf(createPublicChannelRequest.category()))
                .isVoiceChannel(createPublicChannelRequest.isVoiceChannel())
                .build();

        channelService.createChannel(createPublicChannelCommand);

        return ResponseEntity.ok("Public channel created successfully");

    }

    @RequestMapping(value = "/api/channel/private-channel", method = RequestMethod.POST)
    public ResponseEntity<String> createPrivateChannel(@RequestBody ChannelApiDTO.CreatePrivateChannelRequest createPrivateChannelRequest) {

        ChannelDTO.CreatePrivateChannelCommand createPrivateChannelCommand = ChannelDTO.CreatePrivateChannelCommand.builder()
                .category(createPrivateChannelRequest.category())
                .isVoiceChannel(createPrivateChannelRequest.isVoiceChannel())
                .userIdList(createPrivateChannelRequest.userIdList())
                .build();

        channelService.createPrivateChannel(createPrivateChannelCommand);

        return ResponseEntity.ok("Private channel created successfully");

    }

    @RequestMapping(value = "/api/channel/public-channel", method = RequestMethod.PUT)
    public ResponseEntity<String> updatePublicChannel(@RequestBody ChannelApiDTO.UpdateChannelRequest updateChannelRequest) {

        ChannelDTO.UpdateChannelCommand updateChannelCommand = ChannelDTO.UpdateChannelCommand.builder()
                .id(updateChannelRequest.id())
                .channelName(updateChannelRequest.channelName())
                .category(ChannelType.valueOf(updateChannelRequest.category()))
                .isVoiceChannel(updateChannelRequest.isVoiceChannel())
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
                        .build())
                .toList();

    }

}
