package com.orleave.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "email_confirm")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class EmailConfirm {
	
	@Id
	@Column(name = "email", nullable = false, length = 80)
	private String email;
	
	@Column(name = "code", nullable = false, length = 8)
	private String code;
	
	@Column(name = "time", nullable = false)
	@CreatedDate
	@LastModifiedDate
	private LocalDateTime time;
}

