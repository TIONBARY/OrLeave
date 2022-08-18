package com.orleave.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REPORT")
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no", nullable = false, columnDefinition = "INT UNSIGNED")
	private int no;
	
	@ManyToOne()
    @JoinColumn(name = "user_no")
	private User user;
	
	@Column(name = "reported_no", nullable = false)
	private int reported;
	
	@Column(name="category", nullable = false)
	private int category; 
	
	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;
	
	@Column(name = "report_time", nullable = false)
	private LocalDateTime reportTime;
}
