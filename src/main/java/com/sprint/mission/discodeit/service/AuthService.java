package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.UserEntity;

public interface AuthService {

  UserDTO.User login(UserDTO.LoginCommand loginCommand);

}
