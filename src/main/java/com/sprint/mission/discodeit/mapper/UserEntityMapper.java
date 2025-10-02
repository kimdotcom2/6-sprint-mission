package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

  @Mappings({
      @Mapping(target = "profileId", source = "profileId"),
      @Mapping(target = "userStatus", source = "userStatus"),
  })
  UserDTO.User entityToUser(UserEntity userEntity);

  @Mappings({
      @Mapping(target = "profileId", source = "profileId"),
      @Mapping(target = "userStatus", source = "userStatus"),
  })
  UserEntity userToEntity(UserDTO.User user);

}
