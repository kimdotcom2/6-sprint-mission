package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Message extends BaseEntity implements Serializable {

    private String content;
    private boolean isReply;
    private UUID parentMessageId = null;
    private UUID channelId;
    private UUID userId;
    private List<UUID> binaryContentIdList = new ArrayList<>();

    public Message(UUID userId, UUID channelId, String content, boolean isReply, UUID parentMessageId) {
        super();
        this.userId = userId;
        this.channelId = channelId;
        this.content = content;
        this.isReply = isReply;
        this.parentMessageId = parentMessageId;
    }

    public void update(String content, boolean isReply, UUID parentMessageId) {
        this.content = content;
        this.isReply = isReply;
        this.parentMessageId = parentMessageId;
        super.update();
    }

    public void addBinaryContent(UUID binaryContentId) {
        binaryContentIdList.add(binaryContentId);
    }

    public void removeBinaryContent(UUID... binaryContentIds) {
        for (UUID binaryContentId : binaryContentIds) {
            binaryContentIdList.remove(binaryContentId);
        }
    }

    public void removeAllBinaryContent() {
        binaryContentIdList.clear();
    }

    @Override
    public String toString() {
        return "Message [id=" + super.getId() + ", createdAt=" + super.getCreatedAt() + ", updatedAt=" + super.getUpdatedAt() + ", content=" + content
                + ", isReply=" + isReply + ", parentMessageId=" + parentMessageId + ", channelId=" + channelId
                + ", userId=" + userId + "]";
    }

    public static class Builder {

        private String content;
        private boolean isReply;
        private UUID parentMessageId = null;
        private UUID channelId;
        private UUID userId;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder isReply(boolean isReply) {
            this.isReply = isReply;
            return this;
        }

        public Builder parentMessageId(UUID parentMessageId) {
            this.parentMessageId = parentMessageId;
            return this;
        }

        public Builder channelId(UUID channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Message build() {
            return new Message(userId, channelId, content, isReply, parentMessageId);
        }

    }

}
