package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.UserEntity;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil = new SecurityUtil();

    @Override
    public UserEntity login(UserDTO.LoginCommand loginCommand) {

        UserEntity userEntity = userRepository.findByUsername(loginCommand.username())
                .orElseThrow(() -> new NoSuchDataException("사용자를 찾을 수 없음"));

        if (userEntity.getPassword().equals(securityUtil.hashPassword(loginCommand.password()))) {
            return userEntity;
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않음");
        }

    }
}
