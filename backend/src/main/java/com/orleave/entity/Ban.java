package com.orleave.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "BAN")
public class Ban {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no", columnDefinition = "INT UNSIGNED")
	private int no;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
	private User user;
	
	@Column(name = "banned_no", nullable = false, columnDefinition = "INT UNSIGNED")
	private int bannedNo;
}
