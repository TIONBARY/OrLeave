package com.orleave.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UserPersonalityID implements Serializable{
	
	private static final long serialVersionUID = -1573574176415377624L;
	private int user;
	private int personality;
}
