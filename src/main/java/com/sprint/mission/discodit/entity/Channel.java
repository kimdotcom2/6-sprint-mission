package com.sprint.mission.discodit.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel {

    private UUID id;
    private Long createdAt;
    private Long updatedAt;
    private List<User> userList = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private String channelName;
    private String category;
    private boolean isVoiceChannel;

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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isVoiceChannel() {
        return isVoiceChannel;
    }

    public void setVoiceChannel(boolean voiceChannel) {
        isVoiceChannel = voiceChannel;
    }

    public Channel(String channelName, String category, boolean isVoiceChannel) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.channelName = channelName;
        this.category = category;
        this.isVoiceChannel = isVoiceChannel;
    }

    public void update(String channelName, String category, boolean isVoiceChannel) {
        this.channelName = channelName;
        this.category = category;
        this.isVoiceChannel = isVoiceChannel;
        this.updatedAt = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Channel [id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", channelName=" + channelName + ", category=" + category + ", isVoiceChannel=" + isVoiceChannel + "]";
    }

}
