package com.orleave.service;

public interface OauthService {
	public String getKaKaoAccessToken(String code);
	String[] createKakaoUser(String token);
}
