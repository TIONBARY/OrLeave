package com.orleave.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long no;
	@Column(name = "email", nullable = false, unique = true, length = 80)
    String email;
	@Column(nullable = false, name = "pw")
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
	@Column(name = "user_type", nullable = false, length = 16)
	String userType;
	@Column(name = "gender", nullable = false, length = 1)
	String gender;
	@Column(name = "birth_year", nullable = false, columnDefinition = "INT UNSIGNED")
	int birthYear;
	@Column(name = "birth_month", nullable = false, columnDefinition = "INT UNSIGNED")
	int birthMonth;
	@Column(name = "birth_day", nullable = false, columnDefinition = "INT UNSIGNED")
	int birthDay;
	@Column(name = "nickname", nullable = false, unique = true, length = 20)
	String nickname;
	@Column(name = "mbti", nullable = false, length = 4)
	String mbti;
	@Column(name = "religion", nullable = false, columnDefinition = "INT UNSIGNED")
	int religion;
	@Column(name = "smoke", nullable = false, columnDefinition = "INT UNSIGNED")
	int smoke;
	@Column(name = "drink", nullable = false, columnDefinition = "INT UNSIGNED")
	int drink;
	
}
