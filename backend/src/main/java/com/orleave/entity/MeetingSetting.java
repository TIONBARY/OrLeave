package com.orleave.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MEETING_SETTING")
@Getter
@Setter
public class MeetingSetting {
	
	@Id
	@Column(name = "user_no", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long userNo;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "user_no")
	private User user;
	
	@Column(name = "religion", nullable = false, columnDefinition = "INT UNSIGNED")
	private int religion;
	
	@Column(name = "smoke", nullable = false, columnDefinition = "INT UNSIGNED")
	private int smoke;
	
	@Column(name = "drink_min", nullable = false, columnDefinition = "INT UNSIGNED")
	private int drinkMin;
	
	@Column(name = "drink_max", nullable = false, columnDefinition = "INT UNSIGNED")
	private int drinkMax;
	
	@Column(name = "distance", nullable = false, columnDefinition = "INT UNSIGNED")
	private int distance;
	
	@Column(name = "age_min", nullable = false, columnDefinition = "INT UNSIGNED")
	private int ageMin;
	
	@Column(name = "age_max", nullable = false, columnDefinition = "INT UNSIGNED")
	private int ageMax;
	
}