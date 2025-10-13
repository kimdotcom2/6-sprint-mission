package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.UserEntity;

public interface AuthService {

  UserEntity login(UserDTO.LoginCommand loginCommand);

}
