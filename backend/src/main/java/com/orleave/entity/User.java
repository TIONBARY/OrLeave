package com.orleave.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
	
	@Column(name = "email", nullable = false, unique = true, length = 80)
	private String email;
	
	@Column(nullable = false, name = "pw")
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@Column(name = "user_type", nullable = false, length = 16)
	private String userType;
	
	@Column(name = "gender", nullable = false, length = 1)
	private String gender;
	
	@Column(name = "birth_day", nullable = false)
	private LocalDate birthDay;
	
	@Column(name = "nickname", nullable = false, unique = true, length = 20)
	private String nickname;
	
	@Column(name = "mbti", nullable = false, length = 4)
	private String mbti;
	
	@Column(name = "religion", nullable = false, columnDefinition = "INT UNSIGNED")
	private int religion;
	
	@Column(name = "smoke", nullable = false, columnDefinition = "INT UNSIGNED")
	private int smoke;
	
	@Column(name = "drink", nullable = false, columnDefinition = "INT UNSIGNED")
	private int drink;
	
	@Column(name = "image_no", nullable = false, columnDefinition = "INT UNSIGNED")
	private int imageNo;
	
	@OneToOne(mappedBy = "user")
	private MeetingSetting meetingSetting; 
	
	@OneToMany(mappedBy = "user")
	private List<UserInterest> interests = new ArrayList<UserInterest>();
	
	public void addInterest(UserInterest interest) {
		interests.add(interest);
	}
	
	@OneToMany(mappedBy = "user")
	private List<UserPersonality> personalities = new ArrayList<UserPersonality>();
	
	public void addPersonality(UserPersonality personality) {
		personalities.add(personality);
	}
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Inquiry> inquiries = new ArrayList<Inquiry>();
	
	public void addInquiry(Inquiry inquiry) {
		inquiries.add(inquiry);
	}
}
