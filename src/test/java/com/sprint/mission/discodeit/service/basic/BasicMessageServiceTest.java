package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasicMessageServiceTest {

    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ChannelRepository channelRepository;
    @Mock
    private BinaryContentRepository binaryContentRepository;

    @InjectMocks
    private BasicMessageService basicMessageService;

    private MessageDTO.CreateMessageCommand createReq(UUID userId, UUID channelId, String content) {
        return MessageDTO.CreateMessageCommand.builder()
                .userId(userId)
                .channelId(channelId)
                .content(content)
                .isReply(false)
                .parentMessageId(null)
                .binaryContentList(List.of())
                .build();
    }

    /*@Test
    void createMessage_success() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(true);
        when(channelRepository.existById(channelId)).thenReturn(true);

        //when
        basicMessageService.createMessage(createReq(userId, channelId, "hello"));

        //then
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void createMessage_noUser_throws() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(false);

        //when
        MessageDTO.CreateMessageCommand request = createReq(userId, channelId, "hi");

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicMessageService.createMessage(request));
    }

    @Test
    void createMessage_noChannel_throws() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(true);
        when(channelRepository.existById(channelId)).thenReturn(false);

        //when
        MessageDTO.CreateMessageCommand request = createReq(userId, channelId, "hi");

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicMessageService.createMessage(request));
    }

    @Test
    void existMessageById_delegates() {

        UUID id = UUID.randomUUID();
        when(messageRepository.existById(id)).thenReturn(true);
        Assertions.assertTrue(basicMessageService.existMessageById(id));

    }

    @Test
    void findMessageById_success() {

        //given
        UUID userId = UUID.randomUUID();
        UUID channelId = UUID.randomUUID();
        Message message = new Message.Builder()
                .userId(userId)
                .channelId(channelId)
                .content("content")
                .isReply(false)
                .parentMessageId(null)
                .build();
        message.addBinaryContent(UUID.randomUUID());
        when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));

        //when
        MessageDTO.FindMessageResult result = basicMessageService.findMessageById(message.getId()).orElse(null);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(message.getId(), result.id());
        Assertions.assertEquals(1, result.binaryContentList().size());
    }

    @Test
    void findMessageById_notFound_throws() {

        UUID id = UUID.randomUUID();
        when(messageRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicMessageService.findMessageById(id));

    }

    @Test
    void findChildMessagesById_success() {

        //given
        Message parent = new Message.Builder()
                .userId(UUID.randomUUID())
                .channelId(UUID.randomUUID())
                .content("p")
                .isReply(false)
                .parentMessageId(null)
                .build();
        Message child = new Message.Builder()
                .userId(UUID.randomUUID())
                .channelId(parent.getChannelId())
                .content("c")
                .isReply(true)
                .parentMessageId(parent.getId())
                .build();
        when(messageRepository.existById(parent.getId())).thenReturn(true);
        when(messageRepository.findChildById(parent.getId())).thenReturn(List.of(child));

        //when
        List<MessageDTO.FindMessageResult> results = basicMessageService.findChildMessagesById(parent.getId());

        //then
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(child.getId(), results.get(0).id());

    }

    @Test
    void findChildMessagesById_parentNotFound_throws() {

        UUID id = UUID.randomUUID();
        when(messageRepository.existById(id)).thenReturn(false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicMessageService.findChildMessagesById(id));

    }

    @Test
    void findMessagesByUserId_success() {

        //given
        UUID userId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(true);
        Message message = new Message.Builder()
                .userId(userId)
                .channelId(UUID.randomUUID())
                .content("x")
                .isReply(false)
                .parentMessageId(null)
                .build();
        when(messageRepository.findByUserId(userId)).thenReturn(List.of(message));

        //when
        List<MessageDTO.FindMessageResult> resultList = basicMessageService.findMessagesByUserId(userId);

        //then
        Assertions.assertEquals(1, resultList.size());
        Assertions.assertEquals(message.getId(), resultList.get(0).id());
    }

    @Test
    void findMessagesByUserId_userNotFound_throws() {

        UUID userId = UUID.randomUUID();
        when(userRepository.existById(userId)).thenReturn(false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicMessageService.findMessagesByUserId(userId));

    }

    @Test
    void findMessagesByChannelId_success() {

        //given
        UUID channelId = UUID.randomUUID();
        when(channelRepository.existById(channelId)).thenReturn(true);
        Message m = new Message.Builder()
                .userId(UUID.randomUUID())
                .channelId(channelId)
                .content("x")
                .isReply(false)
                .parentMessageId(null)
                .build();
        when(messageRepository.findByChannelId(channelId)).thenReturn(List.of(m));

        //when
        List<MessageDTO.FindMessageResult> results = basicMessageService.findMessagesByChannelId(channelId);

        //then
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(m.getId(), results.get(0).id());

    }

    @Test
    void findMessagesByChannelId_channelNotFound_throws() {

        UUID channelId = UUID.randomUUID();
        when(channelRepository.existById(channelId)).thenReturn(false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicMessageService.findMessagesByChannelId(channelId));

    }

    @Test
    void findAllMessages_success() {

        //given
        Message message = new Message.Builder()
                .userId(UUID.randomUUID())
                .channelId(UUID.randomUUID())
                .content("x")
                .isReply(false)
                .parentMessageId(null)
                .build();
        when(messageRepository.findAll()).thenReturn(List.of(message));

        //when
        List<MessageDTO.FindMessageResult> resultList = basicMessageService.findAllMessages();

        //then
        Assertions.assertEquals(1, resultList.size());
        Assertions.assertEquals(message.getId(), resultList.get(0).id());

    }

    @Test
    void updateMessage_success() {

        //given
        Message existing = new Message.Builder()
                .userId(UUID.randomUUID())
                .channelId(UUID.randomUUID())
                .content("old")
                .isReply(false)
                .parentMessageId(null)
                .build();
        when(messageRepository.existById(existing.getId())).thenReturn(true);
        when(messageRepository.findById(existing.getId())).thenReturn(Optional.of(existing));

        //when
        MessageDTO.UpdateMessageCommand request = MessageDTO.UpdateMessageCommand.builder()
                .id(existing.getId())
                .content("new")
                .isReply(false)
                .parentMessageId(null)
                .build();
        basicMessageService.updateMessage(request);

        //then
        verify(messageRepository, times(1)).save(any(Message.class));

    }

    @Test
    void updateMessage_noSuchMessage_throws() {

        //given
        UUID id = UUID.randomUUID();
        when(messageRepository.existById(id)).thenReturn(false);

        //when
        MessageDTO.UpdateMessageCommand request = MessageDTO.UpdateMessageCommand.builder()
                .id(id)
                .content("x")
                .isReply(false)
                .parentMessageId(null)
                .build();

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicMessageService.updateMessage(request));

    }

    @Test
    void updateMessage_replyWithoutParent_throws() {

        //given
        UUID id = UUID.randomUUID();
        when(messageRepository.existById(id)).thenReturn(true);

        //when
        MessageDTO.UpdateMessageCommand request = MessageDTO.UpdateMessageCommand.builder()
                .id(id)
                .content("x")
                .isReply(true)
                .parentMessageId(null)
                .build();

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicMessageService.updateMessage(request));

    }

    @Test
    void updateMessage_parentGivenButNotReply_throws() {

        //given
        UUID id = UUID.randomUUID();
        when(messageRepository.existById(id)).thenReturn(true);

        //when
        MessageDTO.UpdateMessageCommand request = MessageDTO.UpdateMessageCommand.builder()
                .id(id)
                .content("x")
                .isReply(false)
                .parentMessageId(UUID.randomUUID())
                .build();

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicMessageService.updateMessage(request));

    }

    @Test
    void deleteMessageById_success() {

        //given
        Message existing = new Message.Builder()
                .userId(UUID.randomUUID())
                .channelId(UUID.randomUUID())
                .content("x")
                .isReply(false)
                .parentMessageId(null)
                .build();
        UUID b1 = UUID.randomUUID();
        UUID b2 = UUID.randomUUID();
        existing.addBinaryContent(b1);
        existing.addBinaryContent(b2);
        when(messageRepository.findById(existing.getId())).thenReturn(Optional.of(existing));

        //when
        basicMessageService.deleteMessageById(existing.getId());

        //then
        verify(binaryContentRepository, times(1)).deleteAllByIdIn(argThat(list -> {
            // ensure both ids present
            List<UUID> l = (List<UUID>) list;
            return l.contains(b1) && l.contains(b2) && l.size() == 2;
        }));
        verify(messageRepository, times(1)).deleteById(existing.getId());

    }

    @Test
    void deleteMessageById_notFound_throws() {

        UUID id = UUID.randomUUID();
        when(messageRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> basicMessageService.deleteMessageById(id));

    }*/
}
