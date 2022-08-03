package com.orleave.service;

public interface EmailService {
    void sendSimpleMessage(String to) throws Exception;
    boolean checkCode(String email, String code) throws Exception;
}
