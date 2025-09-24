package com.sprint.mission.discodeit.controller.global;

import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.exception.AllReadyExistDataException;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> NoSuchDataException(NoSuchDataException e) {

        log.error("NoSuchDataException occurred", e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorApiDTO.ErrorApiResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(AllReadyExistDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> AllReadyExistDataException(AllReadyExistDataException e) {

        log.error("AllReadyExistDataException occurred", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorApiDTO.ErrorApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> IllegalArgumentException(IllegalArgumentException e) {

        log.error("IllegalArgumentException occurred", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorApiDTO.ErrorApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> Exception(Exception e) {

        log.error("Exception occurred", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorApiDTO.ErrorApiResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal Server Error")
                .build());
    }

}
