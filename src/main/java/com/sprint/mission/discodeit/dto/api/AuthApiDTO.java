package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthApiDTO {

    public record LoginRequest(
        @JsonProperty("username")
        String nickname,
        String password) {

    }

}
