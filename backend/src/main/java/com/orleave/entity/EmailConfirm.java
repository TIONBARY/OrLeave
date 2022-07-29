package com.orleave.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
public class EmailConfirm {
	
	@Id
	@Column(name = "email", nullable = false, unique = true, length = 80)
	private String email;
	
	@Column(name = "key", nullable = false)
	private String key;
	
	
}

