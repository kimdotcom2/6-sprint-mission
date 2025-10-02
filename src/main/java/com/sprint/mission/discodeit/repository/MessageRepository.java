package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    boolean existById(UUID id);

    Optional<Message> findById(UUID id);

    List<Message> findChildById(UUID id);

    List<Message> findByAuthorId(UUID authorId);

    List<Message> findByChannelId(UUID channelId);

    void deleteById(UUID id);

    void deleteByChannelId(UUID channelId);

}
