package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class User extends BaseEntity implements Serializable {

    private String nickname;
    private String email;
    private String password;
    private String description;
    private UUID profileImageId = null;

    public User(String nickname, String email, String password, String description) {
        super();
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.description = description;
    }

    public void update(String nickname, String email, String password, String description) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.description = description;
        super.update();
    }

    public void updatePassword(String password) {
        this.password = password;
        super.update();
    }

    public void updateProfileImageId(UUID profileImageId) {
        this.profileImageId = profileImageId;
    }

    @Override
    public String toString() {
        return "User [id=" + super.getId() + ", createdAt=" + super.getCreatedAt() + ", updatedAt=" + super.getUpdatedAt() + ", nickname=" + nickname
                + ", email=" + email + ", description=" + description + "]";
    }

    public static class Builder {

        private String nickname;
        private String email;
        private String password;
        private String description;

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public User build() {
            return new User(nickname, email, password, description);
        }

    }

}
