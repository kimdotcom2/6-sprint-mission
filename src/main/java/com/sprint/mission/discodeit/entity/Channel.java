package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.enums.ChannelType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Getter
@RequiredArgsConstructor
public class Channel extends BaseEntity implements Serializable {

    private String channelName;
    private ChannelType category;
    private boolean isVoiceChannel = false;
    private boolean isPrivate = false;
    private String description;

    public Channel(String channelName, ChannelType category, boolean isVoiceChannel, boolean isPrivate, String description) {
        super();
        this.channelName = channelName;
        this.category = category;
        this.isVoiceChannel = isVoiceChannel;
        this.isPrivate = isPrivate;
        this.description = description;
    }

    public void update(String channelName, ChannelType category, boolean isVoiceChannel, String description) {
        this.channelName = channelName;
        this.category = category;
        this.isVoiceChannel = isVoiceChannel;
        this.description = description;
        super.update();
    }

    @Override
    public String toString() {
        return "Channel [id=" + super.getId() + ", createdAt=" + super.getCreatedAt() + ", updatedAt=" + super.getUpdatedAt() + ", channelName=" + channelName + ", category=" + category + ", isVoiceChannel=" + isVoiceChannel + ", isPrivate=" + isPrivate + ", description=" + description + "]";
    }

    public static class Builder {

        private String channelName;
        private ChannelType category;
        private boolean isVoiceChannel;
        private boolean isPrivate;
        private String description;

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

        public Builder isPrivate(boolean isPrivate) {
            this.isPrivate = isPrivate;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Channel build() {
            return new Channel(channelName, category, isVoiceChannel, isPrivate, description);
        }

    }

}
