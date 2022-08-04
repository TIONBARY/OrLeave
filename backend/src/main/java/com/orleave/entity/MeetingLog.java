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
@Table(name = "MEETING_LOG")
public class MeetingLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no", nullable = false, columnDefinition = "INT UNSIGNED")
	private int no;
	
	@ManyToOne()
    @JoinColumn(name = "user1_no")
	private User user1;
	
	@Column(name = "user2_no")
	private int user2_no;
	
	@Column(name = "created_time", nullable = false)
	private LocalDateTime createdTime;

}
