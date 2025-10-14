package com.sprint.mission.discodeit.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class MessageEntity extends BaseUpdatableEntity {

  @Column(nullable = true)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "channel_id", nullable = false)
  private ChannelEntity channel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private UserEntity author;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JoinTable(
      name = "message_attachments",
      joinColumns = @JoinColumn(name = "message_id"),
      inverseJoinColumns = @JoinColumn(name = "attachment_id")
  )
  private List<BinaryContentEntity> attachments = new ArrayList<>();

  @Builder
  public MessageEntity(String content, ChannelEntity channel, UserEntity author) {
    this.content = content;
    this.channel = channel;
    this.author = author;
  }

  public void updateMessage(String content) {
    this.content = content;
  }

}
