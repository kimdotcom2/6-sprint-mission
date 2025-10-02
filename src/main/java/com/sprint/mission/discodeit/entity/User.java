package com.sprint.mission.discodeit.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseUpdatableEntity {

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
  @Column(name = "profile_id", unique = true)
  private BinaryContent profileId;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "user", orphanRemoval = true)
  @Column(nullable = false)
  private UserStatus userStatus;

  public void update(String nickname, String email, String password) {
    this.username = nickname;
    this.email = email;
    this.password = password;
  }

  public void updatePassword(String password) {
    this.password = password;
  }

  public void updateProfile(BinaryContent profileId) {
    this.profileId = profileId;
  }

  public void updateUserStatus(UserStatus userStatus) {
    this.userStatus = userStatus;
  }

  @Override
  public String toString() {
    return "User [id=" + super.getId() + ", createdAt=" + super.getCreatedAt() + ", updatedAt=" + super.getUpdatedAt() + ", nickname=" + username
        + ", email=" + email + "]";
  }

}
