package com.orleave.service;

public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;
    boolean checkCode(String email, String code) throws Exception;
}
