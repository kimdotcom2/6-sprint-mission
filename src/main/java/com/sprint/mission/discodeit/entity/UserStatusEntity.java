package com.sprint.mission.discodeit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_statuses")
public class UserStatusEntity extends BaseUpdatableEntity {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private UserEntity user;

  @Column(nullable = false)
  private Instant lastActiveAt;

  @Builder
  public UserStatusEntity(UserEntity user, Instant lastActiveAt) {
    this.user = user;
    this.lastActiveAt = lastActiveAt;
  }

  public void updateLastActiveAt(Instant lastActiveAt) {
    this.lastActiveAt = lastActiveAt;
  }

}
