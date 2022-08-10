package com.orleave.service;

public interface OauthService {
//	public String getKaKaoAccessToken(String code);
	String createKakaoUser(String token);
	public String getNaverAccessToken(String code);
	String createNaverUser(String token);
	public String getGoogleAccessToken(String code);
	String createGoogleUser(String token);
}
