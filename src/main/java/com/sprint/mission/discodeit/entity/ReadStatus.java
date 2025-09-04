package com.sprint.mission.discodeit.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
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

}
