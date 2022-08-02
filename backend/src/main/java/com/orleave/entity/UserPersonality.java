package com.orleave.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(UserPersonalityID.class)
@Table(name = "USER_PERSONALITY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserPersonality {
	
	@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;
	
    @Id
    @Column(name = "personality_no", columnDefinition = "INT UNSIGNED")
    private int personality;
}
