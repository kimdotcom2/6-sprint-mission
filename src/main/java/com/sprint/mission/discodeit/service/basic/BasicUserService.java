package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.utils.SecurityUtil;
import com.sprint.mission.discodeit.component.Validator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("basicUserService")
@RequiredArgsConstructor
public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final SecurityUtil securityUtil = new SecurityUtil();
    private final Validator validator;

    @Override
    public void createUser(User user) {

        if (userRepository.existById(user.getId())) {
            throw new IllegalArgumentException("User already exists.");
        }

        if (!validator.isPasswordValid(user.getPassword()) || !validator.isEmailValid(user.getEmail()) || user.getNickname().isBlank()) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        if (userRepository.existByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        user.setPassword(securityUtil.hashPassword(user.getPassword()));

        userRepository.save(user);

        userStatusRepository.save(new UserStatus(user.getId()));

    }

    @Override
    public boolean existUserById(UUID id) {
        return userRepository.existById(id);
    }

    @Override
    public boolean existUserByEmail(String email) {
        return userRepository.existByEmail(email);
    }

    @Override
    public Optional<User> findUserById(UUID id) {

        if (!userRepository.existById(id)) {
            throw new IllegalArgumentException("No such user.");
        }

        return userRepository.findById(id);

    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        if (!userRepository.existByEmail(email)) {
            throw new IllegalArgumentException("No such user.");
        }

        return userRepository.findByEmail(email);

    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(UserDTO.UpdateUserRequest request) {

        if (!validator.isEmailValid(request.email()) ||
                !validator.isPasswordValid(request.newPassword()) ||
                !validator.isPasswordValid(request.currentPassword()) ||
                request.nickname().isBlank()) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        User updatedUser = findUserById(request.id()).orElseThrow(() -> new IllegalArgumentException("No such user."));

        if (userRepository.existByEmail(request.email()) && !updatedUser.getId().equals(request.id())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        if (!securityUtil.hashPassword(request.currentPassword()).equals(updatedUser.getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        updatedUser.update(request.nickname(), request.email(), securityUtil.hashPassword(request.currentPassword()), request.description());

        userRepository.save(updatedUser);

    }

    @Override
    public void deleteUserById(UUID id) {

        if (!userRepository.existById(id)) {
            throw new IllegalArgumentException("No such user.");
        }

        userRepository.deleteById(id);

    }
}
