package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.enums.ChannelType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "channels")
public class Channel extends BaseUpdatableEntity {

  @Enumerated(EnumType.STRING)
  private ChannelType type;

  @Column(nullable = true)
  private String name;

  @Column(nullable = true)
  private String description;

}
