package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.ChannelType;

import java.util.UUID;

public class DiscordDTO {

    //user update를 위한 Request DTO
    public record UpdateUserRequest(UUID id, String nickname, String email, String currentPassword, String newPassword, String description) {

        public static class Builder {

            private UUID id;
            private String nickname;
            private String email;
            private String currentPassword;
            private String newPassword;
            private String description;

            public Builder id(UUID id) {
                this.id = id;
                return this;
            }

            public Builder nickname(String nickname) {
                this.nickname = nickname;
                return this;
            }

            public Builder email(String email) {
                this.email = email;
                return this;
            }

            public Builder currentPassword(String currentPassword) {
                this.currentPassword = currentPassword;
                return this;
            }

            public Builder newPassword(String newPassword) {
                this.newPassword = newPassword;
                return this;
            }

            public Builder description(String description) {
                this.description = description;
                return this;
            }

            public UpdateUserRequest build() {
                return new UpdateUserRequest(id, nickname, email, currentPassword, newPassword, description);
            }

        }

    }

    //channel update를 위한 Request DTO
    public record UpdateChannelRequest(UUID id, String channelName, ChannelType category, boolean isVoiceChannel) {

        public static class Builder {

            private UUID id;
            private String channelName;
            private ChannelType category;
            private boolean isVoiceChannel;

            public Builder id(UUID id) {
                this.id = id;
                return this;
            }

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

            public UpdateChannelRequest build() {
                return new UpdateChannelRequest(id, channelName, category, isVoiceChannel);
            }

        }

    }

    //message update를 위한 Request DTO
    public record UpdateMessageRequest(UUID id, String content, boolean isReply, UUID parentMessageId) {

        public static class Builder {
            private UUID id;
            private String content;
            private boolean isReply = false;
            private UUID parentMessageId = null;

            public Builder id(UUID id) {
                this.id = id;
                return this;
            }

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

            public UpdateMessageRequest build() {
                return new UpdateMessageRequest(id, content, isReply, parentMessageId);
            }

        }

    }

}
