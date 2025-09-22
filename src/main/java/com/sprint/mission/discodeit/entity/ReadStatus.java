package com.sprint.mission.discodeit.entity;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ReadStatus extends BaseEntity implements Serializable {

    private UUID channelId;
    private UUID userId;
    private Long lastReadTimestamp;

    public ReadStatus(UUID channelId, UUID userId, Long lastReadTimestamp) {
        super();
        this.channelId = channelId;
        this.userId = userId;
        this.lastReadTimestamp = lastReadTimestamp;
    }

    public void updateLastReadTimestamp(Long lastReadTimestamp) {
        this.lastReadTimestamp = lastReadTimestamp;
    }

    public static class Builder {
        private UUID channelId;
        private UUID userId;
        private Long lastReadTimestamp;

        public Builder channelId(UUID channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder lastReadTimestamp(Long lastReadTimestamp) {
            this.lastReadTimestamp = lastReadTimestamp;
            return this;
        }

        public ReadStatus build() {
            return new ReadStatus(channelId, userId, lastReadTimestamp);
        }
    }

}
