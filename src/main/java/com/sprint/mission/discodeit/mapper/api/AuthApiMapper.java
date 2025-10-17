package com.sprint.mission.discodeit.mapper.api;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.dto.api.BinaryContentApiDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthApiMapper {

  public UserApiDTO.FindUserResponse userToFindUserResponse(UserDTO.User user) {

    return UserApiDTO.FindUserResponse.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .profile(user.getProfileId() != null ?
            BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                .id(user.getProfileId().getId())
                .fileName(user.getProfileId().getFileName())
                .contentType(user.getProfileId().getContentType())
                .build() :
            null)
        .isOnline(user.getIsOnline())
        .build();

  }

}
