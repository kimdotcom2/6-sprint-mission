package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.enums.FileType;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class BinaryContent implements Serializable {

    private static final long serialVersionUID = 1L;
    private final UUID id = UUID.randomUUID();
    private final Long createdAt = Instant.now().toEpochMilli();
    private final byte[] data;
    private final FileType fileType;

}
