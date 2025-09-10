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
    private Long lastReadTimestamp = Instant.now().toEpochMilli();

    public ReadStatus(UUID channelId, UUID userId) {
        super();
        this.channelId = channelId;
        this.userId = userId;
    }

    public void updateLastReadTimestamp() {
        this.lastReadTimestamp = Instant.now().toEpochMilli();
    }

    public static class Builder {
        private UUID channelId;
        private UUID userId;

        public Builder channelId(UUID channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public ReadStatus build() {
            return new ReadStatus(channelId, userId);
        }
    }

}
