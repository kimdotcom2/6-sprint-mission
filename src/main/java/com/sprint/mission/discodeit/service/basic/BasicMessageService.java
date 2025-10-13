package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.dto.MessageDTO.Message;
import com.sprint.mission.discodeit.dto.PagingDTO;
import com.sprint.mission.discodeit.dto.PagingDTO.OffsetPage;
import com.sprint.mission.discodeit.entity.BinaryContentEntity;
import com.sprint.mission.discodeit.entity.MessageEntity;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import com.sprint.mission.discodeit.mapper.MessageEntityMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;
  private final BinaryContentRepository binaryContentRepository;
  private final BinaryContentStorage binaryContentStorage;
  private final MessageEntityMapper messageEntityMapper;

  @Transactional
  @Override
  public MessageDTO.Message createMessage(MessageDTO.CreateMessageCommand request) {

    if (!userRepository.existById(request.userId())) {
      throw new NoSuchDataBaseRecordException("No such user.");
    }

    if (!channelRepository.existById(request.channelId())) {
      throw new NoSuchDataBaseRecordException("No such channel.");
    }

    MessageEntity messageEntity = MessageEntity.builder()
        .author(userRepository.findById(request.userId())
            .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user.")))
        .channel(channelRepository.findById(request.channelId())
            .orElseThrow(() -> new NoSuchDataBaseRecordException("No such channel.")))
        .content(request.content())
        .build();

    if (!request.binaryContentList().isEmpty()) {

      List<BinaryContentEntity> binaryContentList = binaryContentRepository.saveAll(
          request.binaryContentList().stream()
              .map(binaryContent -> BinaryContentEntity.builder()
                  .fileName(binaryContent.fileName())
                  .size((long) binaryContent.data().length)
                  .contentType(binaryContent.contentType())
                  .build())
              .toList());

      for (int i = 0; i < binaryContentList.size(); i++) {
        binaryContentStorage.put(binaryContentList.get(i).getId(),
            request.binaryContentList().get(i).data());
      }

    }

    return messageEntityMapper.entityToMessage(messageRepository.save(messageEntity));

  }

  @Override
  public boolean existMessageById(UUID id) {
    return messageRepository.existById(id);
  }

  @Override
  public Optional<MessageDTO.Message> findMessageById(UUID id) {

    MessageEntity messageEntity = messageRepository.findById(id)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such message."));

    return Optional.ofNullable(messageEntityMapper.entityToMessage(messageEntity));
  }

  @Transactional(readOnly = true)
  @Override
  public OffsetPage<MessageDTO.Message> findMessagesByAuthorId(UUID authorId, PagingDTO.OffsetRequest pageable) {

    if (!userRepository.existById(authorId)) {
      throw new NoSuchDataBaseRecordException("No such user.");
    }

    Page<MessageEntity> paging = messageRepository.findByAuthorId(authorId, PageRequest.of(pageable.getPage(), pageable.getSize()));

    return OffsetPage.<MessageDTO.Message>builder()
        .content(paging.getContent().stream()
            .map(messageEntityMapper::entityToMessage)
            .toList())
        .number(paging.getNumber())
        .size(paging.getSize())
        .hasNext(paging.hasNext())
        .totalElement(paging.getTotalElements())
        .build();

  }

  @Transactional(readOnly = true)
  @Override
  public OffsetPage<MessageDTO.Message> findMessagesByChannelId(UUID channelId, PagingDTO.OffsetRequest pageable) {

    if (!channelRepository.existById(channelId)) {
      throw new NoSuchDataBaseRecordException("No such channel.");
    }

    Page<MessageEntity> paging = messageRepository.findByChannelId(channelId, PageRequest.of(pageable.getPage(), pageable.getSize()));

    return OffsetPage.<MessageDTO.Message>builder()
        .content(paging.getContent().stream()
            .map(messageEntityMapper::entityToMessage)
            .toList())
        .number(paging.getNumber())
        .size(paging.getSize())
        .hasNext(paging.hasNext())
        .totalElement(paging.getTotalElements())
        .build();

  }

  @Override
  public OffsetPage<Message> findAllMessages(PagingDTO.OffsetRequest pageable) {

    Page<MessageEntity> paging = messageRepository.findAll(PageRequest.of(pageable.getPage(), pageable.getSize()));

    return OffsetPage.<MessageDTO.Message>builder()
        .content(paging.getContent().stream()
            .map(messageEntityMapper::entityToMessage)
            .toList())
        .number(paging.getNumber())
        .size(paging.getSize())
        .hasNext(paging.hasNext())
        .totalElement(paging.getTotalElements())
        .build();

  }

  @Transactional
  @Override
  public MessageDTO.Message updateMessage(MessageDTO.UpdateMessageCommand request) {

    if (!messageRepository.existById(request.id())) {
      throw new NoSuchDataBaseRecordException("No such message.");
    }

    MessageEntity updatedMessageEntity = messageRepository.findById(request.id())
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such message."));
    updatedMessageEntity.updateMessage(request.content());

    return messageEntityMapper.entityToMessage(messageRepository.save(updatedMessageEntity));

  }

  @Transactional
  @Override
  public void deleteMessageById(UUID id) {

    binaryContentRepository.deleteAllByIdIn(messageRepository.findById(id)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such message.")).getAttachments()
        .stream()
        .map(BinaryContentEntity::getId)
        .toList());

    messageRepository.deleteById(id);

  }
}
