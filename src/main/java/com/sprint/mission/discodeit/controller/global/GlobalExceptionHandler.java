package com.sprint.mission.discodeit.controller.global;

import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.exception.AllReadyExistDataBaseRecordException;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(NoSuchDataBaseRecordException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorApiDTO.ErrorApiResponse> NoSuchDataBaseRecordException(
      NoSuchDataBaseRecordException e) {

    log.error("NoSuchDataBaseRecordException occurred", e);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorApiDTO.ErrorApiResponse.builder()
        .code(HttpStatus.NOT_FOUND.value())
        .message(e.getMessage())
        .build());
  }

  @ExceptionHandler(AllReadyExistDataBaseRecordException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorApiDTO.ErrorApiResponse> AllReadyExistDataBaseRecordException(
      AllReadyExistDataBaseRecordException e) {

    log.error("AllReadyExistDataBaseRecordException occurred", e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorApiDTO.ErrorApiResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(e.getMessage())
        .build());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorApiDTO.ErrorApiResponse> IllegalArgumentException(
      IllegalArgumentException e) {

    log.error("IllegalArgumentException occurred", e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorApiDTO.ErrorApiResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(e.getMessage())
        .build());
  }

  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorApiDTO.ErrorApiResponse> ValidationException(ValidationException e) {

    log.error("ValidationException occurred", e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorApiDTO.ErrorApiResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(e.getMessage())
        .build());

  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorApiDTO.ErrorApiResponse> Exception(Exception e) {

    log.error("Exception occurred", e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorApiDTO.ErrorApiResponse.builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("Internal Server Error")
            .build());
  }

}
