package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class User extends BaseEntity implements Serializable {

    private String nickname;
    private String email;
    private String password;
    private String description;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
        super.setUpdatedAt();
    }

    @Override
    public String toString() {
        return "User [id=" + super.getId() + ", createdAt=" + super.getCreatedAt() + ", updatedAt=" + super.getUpdatedAt() + ", nickname=" + nickname
                + ", email=" + email + ", description=" + description + "]";
    }



}
