package com.sprint.mission.discodeit.dto.api;

public class AuthApiDTO {

    public record LoginRequest(String nickname, String password) {

    }

}
