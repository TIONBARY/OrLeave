package com.orleave.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
@Table(name = "INQUIRY")
public class Inquiry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inquiry_no", nullable = false, columnDefinition = "INT UNSIGNED")
	private int no;
	
	@ManyToOne()
    @JoinColumn(name = "user_no")
	private User user;
	
	@Column(name = "title", nullable = false, length = 100)
	private String title;
	
	@Column(name = "context", nullable = false, length = 500)
	private String context;
	
	@Column(name = "answer", nullable = false, length = 500)
	private String answer;
	
	@Column(name = "created_time", nullable = false)
	private LocalDateTime createdTime;

}
