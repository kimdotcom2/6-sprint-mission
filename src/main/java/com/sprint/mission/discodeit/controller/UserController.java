package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.dto.UserStatusDTO;
import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO.UserUpdateRequest;
import com.sprint.mission.discodeit.enums.FileType;
import com.sprint.mission.discodeit.exception.AllReadyExistDataException;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

/**
 * 사용자 관련 API 컨트롤러
 */
@Tag(name = "사용자 API", description = "사용자 계정 및 프로필 관리 API")
@RestController
@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final UserStatusService userStatusService;

    /**
     * 사용자 등록
     *
     * @param userCreateRequest 사용자 생성 요청 정보
     * @param profile 프로필 이미지 파일
     * @return 생성된 사용자 정보
     * @throws IOException 파일 처리 중 오류 발생 시
     */
    @Operation(
        summary = "사용자 등록",
        description = "새로운 사용자를 등록합니다.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "사용자 생성 성공",
                content = @Content(schema = @Schema(implementation = UserApiDTO.FindUserResponse.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
            )
        }
    )
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserApiDTO.FindUserResponse> signup(
            @Parameter(description = "사용자 생성 요청 정보", required = true,
                content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UserApiDTO.UserCreateRequest.class)))
            @RequestPart("userCreateRequest") @Valid UserApiDTO.UserCreateRequest userCreateRequest,
            @Parameter(description = "프로필 이미지 파일")
            @RequestPart(value = "profile", required = false) MultipartFile profile)
        throws IOException {

        UserDTO.CreateUserCommand createUserCommand = UserDTO.CreateUserCommand.builder()
                .nickname(userCreateRequest.nickname())
                .email(userCreateRequest.email())
                .password(userCreateRequest.password())
                .description(userCreateRequest.description())
                .profileImage(profile == null ? null : BinaryContentDTO.CreateBinaryContentCommand.builder()
                    .fileName(profile.getName())
                    .data(profile.getBytes())
                    .fileType(FileType.IMAGE)
                    .build())
                .build();

        userService.createUser(createUserCommand);

        UserDTO.FindUserResult user = userService.findUserByEmail(userCreateRequest.email())
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        UserApiDTO.FindUserResponse response = UserApiDTO.FindUserResponse.builder()
                .id(user.id())
                .nickname(user.nickname())
                .email(user.email())
                .profileImageId(user.profileImageId())
                .isOnline(user.isOnline())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.createdAt()), ZoneId.systemDefault()))
                .build();

        return ResponseEntity.status(201).body(response);

    }

    /**
     * 사용자 프로필 수정
     *
     * @param userId 사용자 ID
     * @param userUpdateRequest 사용자 수정 정보
     * @param profile 프로필 이미지 파일
     * @return 수정된 사용자 정보
     * @throws IOException 파일 처리 중 오류 발생 시
     */
    @Operation(
        summary = "사용자 프로필 수정",
        description = "사용자 프로필 정보를 수정합니다.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "프로필 수정 성공",
                content = @Content(schema = @Schema(implementation = UserApiDTO.FindUserResponse.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "사용자를 찾을 수 없음",
                content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
            )
        }
    )
    @PatchMapping(value = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserApiDTO.FindUserResponse> userUpdateProfile(
            @Parameter(description = "사용자 ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID userId,
            @Parameter(description = "사용자 수정 정보", required = true,
                content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UserUpdateRequest.class)))
            @RequestPart @Valid UserUpdateRequest userUpdateRequest,
            @Parameter(description = "프로필 이미지 파일")
            @RequestPart(value = "profile", required = false) MultipartFile profile)
        throws IOException {

        UserDTO.UpdateUserCommand updateUserCommand = UserDTO.UpdateUserCommand.builder()
                .id(userId)
                .nickname(userUpdateRequest.nickname())
                .email(userUpdateRequest.email())
                .currentPassword(userUpdateRequest.currentPassword())
                .newPassword(userUpdateRequest.newPassword())
                .isProfileImageUpdated(!profile.isEmpty())
                .profileImage(profile == null ? null : BinaryContentDTO.CreateBinaryContentCommand.builder()
                    .fileName(profile.getName())
                    .data(profile.getBytes())
                    .fileType(FileType.IMAGE)
                    .build())
                .build();

        userService.updateUser(updateUserCommand);

        UserDTO.FindUserResult user = userService.findUserById(userId)
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        return ResponseEntity.status(204).body(UserApiDTO.FindUserResponse.builder()
                .id(user.id())
                .nickname(user.nickname())
                .email(user.email())
                .profileImageId(user.profileImageId())
                .isOnline(user.isOnline())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.updatedAt()), ZoneId.systemDefault()))
                .build());

    }

    /**
     * 사용자 삭제
     *
     * @param userId 사용자 ID
     * @return 삭제 결과 메시지
     */
    @Operation(
        summary = "사용자 삭제",
        description = "사용자 계정을 삭제합니다.",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "사용자 삭제 성공"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "사용자를 찾을 수 없음",
                content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
            )
        }
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
            @Parameter(description = "사용자 ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID userId) {

        userService.deleteUserById(userId);

        return ResponseEntity.status(204).body("User deleted successfully");

    }

    /**
     * 전체 사용자 조회
     *
     * @return 사용자 목록
     */
    @Operation(
        summary = "전체 사용자 조회",
        description = "모든 사용자 정보를 조회합니다.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "사용자 목록 조회 성공",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserApiDTO.FindUserResponse.class)))
            )
        }
    )
    @GetMapping()
    public ResponseEntity<List<UserApiDTO.FindUserResponse>> findAll() {

        List<UserApiDTO.FindUserResponse> userList = userService.findAllUsers().stream()
                .map(user -> UserApiDTO.FindUserResponse.builder()
                        .id(user.id())
                        .nickname(user.nickname())
                        .email(user.email())
                        .profileImageId(user.profileImageId())
                        .isOnline(user.isOnline())
                        .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.createdAt()), ZoneId.systemDefault()))
                        .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.updatedAt()), ZoneId.systemDefault()))
                        .build())

                .toList();

        return ResponseEntity.status(201).body(userList);

    }

    /**
     * 사용자 온라인 상태 확인
     *
     * @param userId 사용자 ID
     * @return 사용자 온라인 상태 정보
     */
    @Operation(
        summary = "사용자 온라인 상태 확인",
        description = "사용자의 온라인 상태를 확인합니다.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "상태 조회 성공",
                content = @Content(schema = @Schema(implementation = UserApiDTO.CheckUserOnline.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "사용자를 찾을 수 없음",
                content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
            )
        }
    )
    @GetMapping("/{userId}/userStatus")
    public ResponseEntity<UserApiDTO.CheckUserOnline> checkUserOnlineStatus(
            @Parameter(description = "사용자 ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID userId) {

        UserDTO.FindUserResult user = userService.findUserById(userId).get();

        return ResponseEntity.ok(UserApiDTO.CheckUserOnline.builder()
                .id(user.id())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.updatedAt()), ZoneId.systemDefault()))
                .userId(user.id())
                .lastOnlineAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.updatedAt()), ZoneId.systemDefault()))
                .isOnline(true)
                .build());

    }

    /**
     * 사용자 온라인 상태 업데이트
     *
     * @param userId 사용자 ID
     * @param userStatusUpdateRequest 상태 업데이트 요청 정보
     * @return 업데이트된 사용자 상태 정보
     */
    @Operation(
        summary = "사용자 온라인 상태 업데이트",
        description = "사용자의 온라인 상태를 업데이트합니다.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "상태 업데이트 성공",
                content = @Content(schema = @Schema(implementation = UserApiDTO.CheckUserOnline.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "사용자를 찾을 수 없음",
                content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
            )
        }
    )
    @PatchMapping(value = "/{userId}/userStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserApiDTO.CheckUserOnline> updateUserOnlineStatus(
            @Parameter(description = "사용자 ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "사용자 상태 업데이트 정보",
                required = true,
                content = @Content(schema = @Schema(implementation = UserApiDTO.UserStatusUpdateRequest.class))
            )
            @RequestBody @Valid UserApiDTO.UserStatusUpdateRequest userStatusUpdateRequest) {

        UserStatusDTO.FindUserStatusResult userStatus = userStatusService.findUserStatusByUserId(userId)
            .orElseThrow(() -> new NoSuchDataException("No such user status."));

        userStatusService.updateUserStatus(UserStatusDTO.UpdateUserStatusCommand.builder()
                .id(userStatus.id())
                .lastActiveTimestamp(userStatusUpdateRequest.newLastActiveAt().toEpochSecond(ZoneOffset.UTC))
                .build());

        return ResponseEntity.ok(UserApiDTO.CheckUserOnline.builder()
                .id(userStatus.id())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(userStatus.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(userStatus.updatedAt()), ZoneId.systemDefault()))
                .userId(userStatus.userId())
                .lastOnlineAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(userStatus.lastActiveTimestamp()), ZoneId.systemDefault()))
                .isOnline(true)
                .build());

    }

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> NoSuchDataException(NoSuchDataException e) {

      log.error("NoSuchDataException occurred", e);

      return ResponseEntity.status(404).body(ErrorApiDTO.ErrorApiResponse.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .message(e.getMessage())
            .build());
    }

    @ExceptionHandler(AllReadyExistDataException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> AllReadyExistDataException(AllReadyExistDataException e) {

      log.error("AllReadyExistDataException occurred", e);

      return ResponseEntity.status(400).body(ErrorApiDTO.ErrorApiResponse.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message(e.getMessage())
            .build());
    }

}
