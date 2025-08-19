package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID id;
    private Long createdAt;
    private Long updatedAt;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
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
        this.userId = userId;
        this.channelId = channelId;
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.content = content;
        this.isReply = isReply;
        this.parentMessageId = parentMessageId;
    }

    public void update(String content, boolean isReply, UUID parentMessageId) {
        this.content = content;
        this.isReply = isReply;
        this.parentMessageId = parentMessageId;
        this.updatedAt = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", content=" + content
                + ", isReply=" + isReply + ", parentMessageId=" + parentMessageId + ", channelId=" + channelId
                + ", userId=" + userId + "]";
    }

}
