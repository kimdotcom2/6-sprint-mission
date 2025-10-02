package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.enums.ChannelType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "channels")
public class ChannelEntity extends BaseUpdatableEntity {

  @Enumerated(EnumType.STRING)
  private ChannelType type;

  @Column(nullable = true)
  private String name;

  @Column(nullable = true)
  private String description;

  @Builder
  public ChannelEntity(ChannelType type, String name, String description) {
    this.type = type;
    this.name = name;
    this.description = description;
  }

  public boolean isPrivate() {
    return type == ChannelType.PRIVATE;
  }

  public void update(String name, String description) {
    this.name = name;
    this.description = description;
  }

}
