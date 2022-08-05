package com.orleave.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NOTICE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no", nullable = false, columnDefinition = "INT UNSIGNED")
	private int no;
	
	@Column(name = "title", nullable = false, length = 100)
	private String title;
	
	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;
	
	@Column(name = "created_time", nullable = false)
	@CreatedDate
	private LocalDateTime createdTime;
}
