package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.enums.FileType;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
public class BinaryContent implements Serializable {

    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Long createdAt = Instant.now().toEpochMilli();
    private final FileType fileType;

}
