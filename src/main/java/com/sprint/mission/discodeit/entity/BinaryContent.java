package com.sprint.mission.discodeit.entity;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
public class BinaryContent implements Serializable {

    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final Long createdAt;

}
