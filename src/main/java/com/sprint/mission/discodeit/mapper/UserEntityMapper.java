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
      @Mapping(target = "isOnline", expression = "java(userEntity.isOnline())")
  })
  UserDTO.User entityToUser(UserEntity userEntity);

  @Mappings({
      //@Mapping(target = "profileId", source = "profileId")
  })
  UserEntity userToEntity(UserDTO.User user);

}
