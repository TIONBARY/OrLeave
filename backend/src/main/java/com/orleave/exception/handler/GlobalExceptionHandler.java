package com.orleave.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.orleave.dto.response.BaseResponseDto;
import com.orleave.exception.MatchingUserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MatchingUserNotFoundException.class)
    protected ResponseEntity<BaseResponseDto> handleMatchingUserNotFoundException(MatchingUserNotFoundException e) {
        return ResponseEntity.status(400).body(BaseResponseDto.of(400, "User Not Exist In Matching"));
    }
	
	@ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<BaseResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Invalid Input"));
    }
	
	@ExceptionHandler(TokenExpiredException.class)
    protected ResponseEntity<BaseResponseDto> handleTokenExpiredException(TokenExpiredException e) {
        return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
    }
}
