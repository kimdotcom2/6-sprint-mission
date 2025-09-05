package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service("basicChannelService")
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;

    @Override
    public void createChannel(ChannelDTO.CreatePublicChannelRequest request) {

        Channel channel = new Channel.Builder()
                .channelName(request.channelName())
                .category(request.category())
                .isVoiceChannel(request.isVoiceChannel())
                .build();

        if (channelRepository.existById(channel.getId())) {
            throw new IllegalArgumentException("Channel already exists.");
        }

        if (channel.getChannelName().isBlank() || channel.getCategory() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        channelRepository.save(channel);

    }

    @Override
    public void createPrivateChannel(ChannelDTO.CreatePrivateChannelRequest request) {

        Channel channel = new Channel.Builder()
                .category(request.category())
                .isPrivate(true)
                .build();

        if (channelRepository.existById(channel.getId())) {
            throw new IllegalArgumentException("Channel already exists.");
        }

        if (channel.getCategory() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        List<ReadStatus> readStatusList = request.userIdList().stream()
                .map(userId -> new ReadStatus(channel.getId(), userId))
                .toList();

        readStatusRepository.saveAll(readStatusList);
        channelRepository.save(channel);

    }

    /*@Override
    public void addUserToChannel(UUID channelId, User user) {

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such user.")).getUserMap().containsKey(user.getId())) {
            throw new IllegalArgumentException("User already exists in channel.");
        }

        Channel updatedChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));
        updatedChannel.getUserMap().put(user.getId(), user);

        channelRepository.save(updatedChannel);

    }*/

    /*@Override
    public void addMessageToChannel(UUID channelId, Message message) {

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        Channel updatedChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));
        updatedChannel.getMessageMap().put(message.getId(), message);

        channelRepository.save(updatedChannel);

    }*/

    @Override
    public boolean existChannelById(UUID id) {
        return channelRepository.existById(id);
    }

    @Override
    public Optional<ChannelDTO.FindChannelResult> findChannelById(UUID id) {

        if (!channelRepository.existById(id)) {
            throw new IllegalArgumentException("No such channel.");
        }

        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));

        return Optional.of(ChannelDTO.FindChannelResult.builder()
                .id(channel.getId())
                .channelName(channel.getChannelName())
                .category(channel.getCategory())
                .isVoiceChannel(channel.isVoiceChannel())
                .isPrivate(channel.isPrivate())
                .userIdList(channel.isPrivate() ? readStatusRepository.findByChannelId(channel.getId()).stream()
                        .map(ReadStatus::getUserId).toList() : new ArrayList<>())
                .recentMessageTime(messageRepository.findByChannelId(channel.getId()).stream()
                        .max(Comparator.comparing(Message::getCreatedAt)).map(Message::getCreatedAt).orElse(null))
                .build());

    }

    @Override
    public List<ChannelDTO.FindChannelResult> findChannelsByUserId(UUID userId) {
        return readStatusRepository.findByUserId(userId).stream()
                .map(readStatus -> channelRepository.findById(readStatus.getChannelId())
                        .orElseThrow(() -> new IllegalArgumentException("No such channel.")))
                        .map(channel -> ChannelDTO.FindChannelResult.builder()
                        .id(channel.getId())
                        .channelName(channel.getChannelName())
                        .category(channel.getCategory())
                        .isVoiceChannel(channel.isVoiceChannel())
                        .isPrivate(channel.isPrivate())
                        .userIdList(channel.isPrivate() ? readStatusRepository.findByChannelId(channel.getId()).stream()
                                .map(ReadStatus::getUserId).toList() : new ArrayList<>())
                                .recentMessageTime(messageRepository.findByChannelId(channel.getId()).stream()
                                        .max(Comparator.comparing(Message::getCreatedAt)).map(Message::getCreatedAt).orElse(null))
                        .build())
                        .toList();
    }

    /*@Override
    public List<Channel> findChannelsByUserId(UUID userId) {
        return findAllChannels().stream()
                .filter(channel -> channel.getUserMap().containsKey(userId))
                .toList();
    }*/

    @Override
    public List<ChannelDTO.FindChannelResult> findAllChannels() {
        return channelRepository.findAll().stream()
                .map(channel -> ChannelDTO.FindChannelResult.builder()
                        .id(channel.getId())
                        .channelName(channel.getChannelName())
                        .category(channel.getCategory())
                        .isVoiceChannel(channel.isVoiceChannel())
                        .isPrivate(channel.isPrivate())
                        .userIdList(channel.isPrivate() ? readStatusRepository.findByChannelId(channel.getId()).stream()
                                .map(ReadStatus::getUserId).toList() : new ArrayList<>())
                        .recentMessageTime(messageRepository.findByChannelId(channel.getId()).stream()
                                .max(Comparator.comparing(Message::getCreatedAt)).map(Message::getCreatedAt).orElse(null))
                        .build())
                .toList();
    }

    @Override
    public void updateChannel(ChannelDTO.UpdateChannelRequest request) {

        if (!channelRepository.existById(request.id())) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (request.channelName().isBlank() || request.category() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        Channel updatedChannel = channelRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));

        if (updatedChannel.isPrivate()) {
            throw new IllegalArgumentException("Private channel cannot be updated.");
        }

        updatedChannel.update(request.channelName(), request.category(), request.isVoiceChannel());
        channelRepository.save(updatedChannel);

    }

    @Override
    public void deleteChannelById(UUID id) {

        if (!channelRepository.existById(id)) {
            throw new IllegalArgumentException("No such channel.");
        }

        messageRepository.deleteByChannelId(id);
        readStatusRepository.deleteByChannelId(id);
        channelRepository.deleteById(id);

    }

    /*@Override
    public void deleteUserFromChannel(UUID channelId, UUID userId) {

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));

        channel.getUserMap().remove(userId);
        channelRepository.save(channel);

    }

    @Override
    public void deleteMessageFromChannel(UUID channelId, UUID messageId) {

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));

        channel.getMessageMap().remove(messageId);
        channelRepository.save(channel);

    }*/
}
