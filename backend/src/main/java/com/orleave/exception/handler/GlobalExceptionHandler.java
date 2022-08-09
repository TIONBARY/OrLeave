package com.orleave.exception.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.orleave.dto.response.BaseResponseDto;
import com.orleave.exception.BanNotFoundException;
import com.orleave.exception.EmailConfirmNotFoundException;
import com.orleave.exception.EmailTimeoutException;
import com.orleave.exception.InquiryNotFoundException;
import com.orleave.exception.LoginProhibitedException;
import com.orleave.exception.MatchingUserNotFoundException;
import com.orleave.exception.NoticeNotFoundException;
import com.orleave.exception.ReportNotFoundException;
import com.orleave.exception.UserNotFoundException;
import com.orleave.exception.WrongPasswordException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MatchingUserNotFoundException.class)
    protected ResponseEntity<BaseResponseDto> handleMatchingUserNotFoundException(MatchingUserNotFoundException e) {
        return ResponseEntity.status(404).body(BaseResponseDto.of(404, "User Not Exist In Matching"));
    }
	
	@ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<BaseResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Invalid Input"));
    }
	
	@ExceptionHandler(TokenExpiredException.class)
    protected ResponseEntity<BaseResponseDto> handleTokenExpiredException(TokenExpiredException e) {
        return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
    }
	
	@ExceptionHandler(BanNotFoundException.class)
    protected ResponseEntity<BaseResponseDto> handleBanNotFoundException(BanNotFoundException e) {
        return ResponseEntity.status(404).body(BaseResponseDto.of(404, "Ban Not Found"));
    }
	
	@ExceptionHandler(EmailConfirmNotFoundException.class)
    protected ResponseEntity<BaseResponseDto> handleEmailConfirmNotFoundException(EmailConfirmNotFoundException e) {
        return ResponseEntity.status(404).body(BaseResponseDto.of(404, "Code Not Sent"));
    }
	
	@ExceptionHandler(EmailTimeoutException.class)
    protected ResponseEntity<BaseResponseDto> handleEmailTimeoutException(EmailTimeoutException e) {
        return ResponseEntity.status(408).body(BaseResponseDto.of(408, "Email Confirm Time Out"));
    }
	
	@ExceptionHandler(InquiryNotFoundException.class)
    protected ResponseEntity<BaseResponseDto> handleInquiryNotFoundException(InquiryNotFoundException e) {
        return ResponseEntity.status(404).body(BaseResponseDto.of(404, "Inquiry Not Found"));
    }
	
	@ExceptionHandler(LoginProhibitedException.class)
    protected ResponseEntity<BaseResponseDto> handleLoginProhibitedException(LoginProhibitedException e) {
        return ResponseEntity.status(403).body(BaseResponseDto.of(403, "Login Prohibited"));
    }
	
	@ExceptionHandler(NoticeNotFoundException.class)
    protected ResponseEntity<BaseResponseDto> handleNoticeNotFoundException(NoticeNotFoundException e) {
        return ResponseEntity.status(404).body(BaseResponseDto.of(404, "Notice Not Found"));
    }
	
	@ExceptionHandler(ReportNotFoundException.class)
    protected ResponseEntity<BaseResponseDto> handleReportNotFoundException(ReportNotFoundException e) {
        return ResponseEntity.status(404).body(BaseResponseDto.of(404, "Report Not Found"));
    }
	
	@ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<BaseResponseDto> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(404).body(BaseResponseDto.of(404, "User Not Found"));
    }
	
	@ExceptionHandler(WrongPasswordException.class)
    protected ResponseEntity<BaseResponseDto> handleWrongPasswordException(WrongPasswordException e) {
        return ResponseEntity.status(404).body(BaseResponseDto.of(404, "Wrong Password"));
    }
	
//	@ExceptionHandler(DataIntegrityViolationException.class)
//    protected ResponseEntity<BaseResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
//        return ResponseEntity.status(404).body(BaseResponseDto.of(404, "Data Integrity Violated"));
//    }
}
