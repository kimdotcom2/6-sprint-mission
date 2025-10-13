package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserStatusDTO;
import com.sprint.mission.discodeit.entity.UserStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserStatusEntityMapper {

  @Mappings({
      @Mapping(target = "userId", source = "user.id")
  })
  UserStatusDTO.UserStatus entityToUserStatus(UserStatusEntity entity);

  @Mappings({
      //@Mapping(target = "user.id", source = "userId")
  })
  UserStatusEntity userStatusToEntity(UserStatusDTO.UserStatus userStatus);

}
