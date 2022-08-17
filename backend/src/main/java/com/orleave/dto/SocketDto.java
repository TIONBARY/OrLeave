package com.orleave.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketDto {
	// 유저의 이름을 저장하기 위한 변수
    private String userName;

    // 메시지의 내용을 저장하기 위한 변수
    private String content;
}
