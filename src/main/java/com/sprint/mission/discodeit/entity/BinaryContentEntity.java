package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.enums.ContentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "binary_contents")
public class BinaryContentEntity extends BaseEntity {

  @Column(nullable = false)
  private String fileName;

  @Column(nullable = false)
  private Long size;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ContentType contentType;

  @Builder
  public BinaryContentEntity(String fileName, Long size, ContentType contentType) {
    this.fileName = fileName;
    this.size = size;
    this.contentType = contentType;
  }

}
