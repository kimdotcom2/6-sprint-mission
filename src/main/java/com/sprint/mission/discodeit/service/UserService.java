package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

  UserDTO.User createUser(UserDTO.CreateUserCommand request);

  boolean existUserById(UUID id);

  boolean existUserByEmail(String email);

  boolean existUserByUsername(String nickname);

  Optional<UserDTO.User> findUserById(UUID id);

  Optional<UserDTO.User> findUserByEmail(String email);

  Optional<UserDTO.User> findUserByUsername(String username);

  List<UserDTO.User> findAllUsers();

  UserDTO.User updateUser(UserDTO.UpdateUserCommand request);

  void deleteUserById(UUID id);

}
