package com.sprint.mission.discodeit.entity;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UserStatus extends BaseEntity implements Serializable {

    private UUID userId;
    private Long lastActiveTimestamp = Instant.now().toEpochMilli();

    public boolean isLogin() {

        //1000ms = 1초
        return ((Instant.now().toEpochMilli() - lastActiveTimestamp) <= 1000 * 60 * 5);

    }

    public UserStatus(UUID userId) {
        super();
        this.userId = userId;
    }

    public void updateLastActiveTimestamp() {
        this.lastActiveTimestamp = Instant.now().toEpochMilli();
    }

    public static class Builder {
        private UUID userId;

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public UserStatus build() {
            return new UserStatus(userId);
        }
    }

}
