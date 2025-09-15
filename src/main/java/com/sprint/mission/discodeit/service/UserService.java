package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    void createUser(UserDTO.CreateUserCommand request);

    boolean existUserById(UUID id);

    boolean existUserByEmail(String email);

    boolean existUserByNickname(String nickname);

    Optional<UserDTO.FindUserResult> findUserById(UUID id);

    Optional<UserDTO.FindUserResult> findUserByEmail(String email);

    Optional<UserDTO.FindUserResult> findUserByNickname(String nickname);

    List<UserDTO.FindUserResult> findAllUsers();

    void updateUser(UserDTO.UpdateUserCommand request);

    void deleteUserById(UUID id);

}
