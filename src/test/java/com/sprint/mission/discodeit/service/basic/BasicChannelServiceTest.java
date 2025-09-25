package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasicChannelServiceTest {

    @Mock
    private ChannelRepository channelRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private ReadStatusRepository readStatusRepository;

    @InjectMocks
    private BasicChannelService basicChannelService;

    private Channel publicChannel() {
        return new Channel.Builder()
                .channelName("general")
                .category(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();
    }

    /*@Test
    void createChannel_success() {
        
        //given
        ChannelDTO.CreatePublicChannelCommand request = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName("general")
                .category(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();
        
        //when
        basicChannelService.createChannel(request);
        
        // then
        verify(channelRepository, times(1)).save(any(Channel.class));
        
    }

    @Test
    void createChannel_invalid_throws() {
        
        //given
        ChannelDTO.CreatePublicChannelCommand request = ChannelDTO.CreatePublicChannelCommand.builder()
                .channelName("")
                .category(null)
                .isVoiceChannel(false)
                .build();

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicChannelService.createChannel(request));

    }

    @Test
    void createPrivateChannel_success() {

        //given
        UUID u1 = UUID.randomUUID();
        UUID u2 = UUID.randomUUID();
        ChannelDTO.CreatePrivateChannelCommand request = ChannelDTO.CreatePrivateChannelCommand.builder()
                .category(ChannelType.TEXT)
                .userIdList(List.of(u1, u2))
                .build();

        //when
        basicChannelService.createPrivateChannel(request);

        //then
        verify(readStatusRepository, times(1)).saveAll(anyList());
        verify(channelRepository, times(1)).save(any(Channel.class));
    }

    @Test
    void createPrivateChannel_invalid_throws() {

        //given
        ChannelDTO.CreatePrivateChannelCommand request = ChannelDTO.CreatePrivateChannelCommand.builder()
                .category(null)
                .userIdList(new ArrayList<>())
                .build();

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicChannelService.createPrivateChannel(request));
    }

    @Test
    void existChannelById_delegates() {

        //given
        UUID id = UUID.randomUUID();
        when(channelRepository.existById(id)).thenReturn(true);

        //then
        Assertions.assertTrue(basicChannelService.existChannelById(id));

    }

    @Test
    void findChannelById_success() {

        //given
        Channel channel = publicChannel();
        when(channelRepository.findById(channel.getId())).thenReturn(Optional.of(channel));
        when(messageRepository.findByChannelId(channel.getId())).thenReturn(List.of());

        //when
        ChannelDTO.FindChannelResult result = basicChannelService.findChannelById(channel.getId()).orElse(null);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(channel.getId(), result.id());
        Assertions.assertEquals(channel.getChannelName(), result.channelName());
        Assertions.assertFalse(result.isPrivate());
    }

    @Test
    void findChannelById_notFound_throws() {

        UUID id = UUID.randomUUID();
        when(channelRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicChannelService.findChannelById(id));

    }

    @Test
    void findChannelsByUserId_success() {

        //given
        Channel channel = publicChannel();
        UUID userId = UUID.randomUUID();
        when(readStatusRepository.findByUserId(userId)).thenReturn(List.of(new ReadStatus(channel.getId(), userId, 0L)));
        when(channelRepository.findById(channel.getId())).thenReturn(Optional.of(channel));
        when(messageRepository.findByChannelId(channel.getId())).thenReturn(List.of());
        when(readStatusRepository.findByChannelId(channel.getId())).thenReturn(List.of());

        //when
        List<ChannelDTO.FindChannelResult> results = basicChannelService.findChannelsByUserId(userId);

        //then
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(channel.getId(), results.get(0).id());
    }

    @Test
    void findAllChannels_success() {

        //given
        Channel channel = publicChannel();
        when(channelRepository.findAll()).thenReturn(List.of(channel));
        when(messageRepository.findByChannelId(channel.getId())).thenReturn(List.of());
        when(readStatusRepository.findByChannelId(channel.getId())).thenReturn(List.of());

        //when
        List<ChannelDTO.FindChannelResult> results = basicChannelService.findAllChannels();

        //then
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(channel.getId(), results.get(0).id());
    }

    @Test
    void updateChannel_success() {

        //given
        Channel channel = publicChannel();
        when(channelRepository.existById(channel.getId())).thenReturn(true);
        when(channelRepository.findById(channel.getId())).thenReturn(Optional.of(channel));

        //when
        ChannelDTO.UpdateChannelCommand request = ChannelDTO.UpdateChannelCommand.builder()
                .id(channel.getId())
                .channelName("general-2")
                .category(ChannelType.TEXT)
                .isVoiceChannel(true)
                .build();

        //then
        basicChannelService.updateChannel(request);
        verify(channelRepository, times(1)).save(any(Channel.class));
    }

    @Test
    void updateChannel_private_throws() {

        //given
        Channel privateChannel = new Channel.Builder()
                .category(ChannelType.TEXT)
                .isPrivate(true)
                .build();
        when(channelRepository.existById(privateChannel.getId())).thenReturn(true);
        when(channelRepository.findById(privateChannel.getId())).thenReturn(Optional.of(privateChannel));

        //when
        ChannelDTO.UpdateChannelCommand request = ChannelDTO.UpdateChannelCommand.builder()
                .id(privateChannel.getId())
                .channelName("x")
                .category(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicChannelService.updateChannel(request));
    }

    @Test
    void updateChannel_invalid_or_missing_throws() {

        //given
        UUID id = UUID.randomUUID();
        when(channelRepository.existById(id)).thenReturn(false);

        //when
        ChannelDTO.UpdateChannelCommand reqMissing = ChannelDTO.UpdateChannelCommand.builder()
                .id(id)
                .channelName("name")
                .category(ChannelType.TEXT)
                .isVoiceChannel(false)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicChannelService.updateChannel(reqMissing));

        ChannelDTO.UpdateChannelCommand invalid = ChannelDTO.UpdateChannelCommand.builder()
                .id(UUID.randomUUID())
                .channelName("")
                .category(null)
                .isVoiceChannel(false)
                .build();
        when(channelRepository.existById(invalid.id())).thenReturn(true);
        when(channelRepository.findById(invalid.id())).thenReturn(Optional.of(publicChannel()));

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicChannelService.updateChannel(invalid));
    }

    @Test
    void deleteChannelById_success() {

        //given
        UUID id = UUID.randomUUID();
        when(channelRepository.existById(id)).thenReturn(true);

        //when
        basicChannelService.deleteChannelById(id);

        //then
        verify(messageRepository, times(1)).deleteByChannelId(id);
        verify(readStatusRepository, times(1)).deleteByChannelId(id);
        verify(channelRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteChannelById_noSuchChannel_throws() {

        UUID id = UUID.randomUUID();
        when(channelRepository.existById(id)).thenReturn(false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicChannelService.deleteChannelById(id));

    }*/
}
