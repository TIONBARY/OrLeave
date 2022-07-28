package com.orleave.entity;

import java.time.LocalDateTime;

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
@Table(name = "login_trial")
@Getter
@Setter
public class LoginTrial {
	@Id
	@Column(name = "user_no", nullable = false)
	private int userNo;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "user_no")
	private User user;
	
	@Column(name = "count", nullable = false, columnDefinition = "INT UNSIGNED")
	private int count;
	
	@Column(name = "time", nullable = false )
	private LocalDateTime time;
}

