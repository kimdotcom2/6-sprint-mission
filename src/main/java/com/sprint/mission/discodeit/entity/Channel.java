package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.enums.ChannelType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class Channel extends BaseEntity implements Serializable {

    private Map<UUID, User> userMap = new TreeMap<>();
    private Map<UUID, Message> messageMap = new LinkedHashMap<>();
    private String channelName;
    private ChannelType category;
    private boolean isVoiceChannel;

    public Map<UUID, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<UUID, User> userMap) {
        this.userMap = userMap;
    }

    public Map<UUID, Message> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(Map<UUID, Message> messageMap) {
        this.messageMap = messageMap;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public ChannelType getCategory() {
        return category;
    }

    public void setCategory(ChannelType category) {
        this.category = category;
    }

    public boolean isVoiceChannel() {
        return isVoiceChannel;
    }

    public void setVoiceChannel(boolean voiceChannel) {
        isVoiceChannel = voiceChannel;
    }

    public Channel(String channelName, ChannelType category, boolean isVoiceChannel) {
        super();
        this.channelName = channelName;
        this.category = category;
        this.isVoiceChannel = isVoiceChannel;
    }

    public void update(String channelName, ChannelType category, boolean isVoiceChannel) {
        this.channelName = channelName;
        this.category = category;
        this.isVoiceChannel = isVoiceChannel;
        super.setUpdatedAt();
    }

    @Override
    public String toString() {
        return "Channel [id=" + super.getId() + ", createdAt=" + super.getCreatedAt() + ", updatedAt=" + super.getUpdatedAt() + ", channelName=" + channelName + ", category=" + category + ", isVoiceChannel=" + isVoiceChannel + "]";
    }

    public static class Builder {

        private String channelName;
        private ChannelType category;
        private boolean isVoiceChannel;

        public Builder channelName(String channelName) {
            this.channelName = channelName;
            return this;
        }

        public Builder category(ChannelType category) {
            this.category = category;
            return this;
        }

        public Builder isVoiceChannel(boolean isVoiceChannel) {
            this.isVoiceChannel = isVoiceChannel;
            return this;
        }

        public Channel build() {
            return new Channel(channelName, category, isVoiceChannel);
        }

    }

}
