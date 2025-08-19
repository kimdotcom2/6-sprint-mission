package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Message extends BaseEntity implements Serializable {

    private String content;
    private boolean isReply;
    private UUID parentMessageId;
    private UUID channelId;
    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public void setChannelId(UUID channelId) {
        this.channelId = channelId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    public UUID getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(UUID parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

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
        super.setUpdatedAt();
    }

    @Override
    public String toString() {
        return "Message [id=" + super.getId() + ", createdAt=" + super.getCreatedAt() + ", updatedAt=" + super.getUpdatedAt() + ", content=" + content
                + ", isReply=" + isReply + ", parentMessageId=" + parentMessageId + ", channelId=" + channelId
                + ", userId=" + userId + "]";
    }

}
