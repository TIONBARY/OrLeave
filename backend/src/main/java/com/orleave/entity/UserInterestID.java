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
public class UserInterestID implements Serializable{
	
	private static final long serialVersionUID = 7532482466716062979L;
	private int user;
	private int interest;
}
