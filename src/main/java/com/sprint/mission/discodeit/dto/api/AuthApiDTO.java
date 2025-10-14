package com.sprint.mission.discodeit.dto.api;

public class AuthApiDTO {

  public record LoginRequest(

      String username,
      String password) {

  }

}
