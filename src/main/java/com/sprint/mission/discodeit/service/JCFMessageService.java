package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.UUID;

public interface JCFMessageService {

    void create(Message message);

    boolean existById(String id);

    void readById(String id);

    void readAll();

    void update(String id, String content, boolean isReply, UUID parentMessageId);

    void deleteById(String id);

}
