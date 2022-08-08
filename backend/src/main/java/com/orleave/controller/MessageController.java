package com.orleave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.orleave.dto.message.Chat;
import com.orleave.dto.message.MatchingReceiveInfo;
import com.orleave.dto.message.MatchingSendInfo;
import com.orleave.entity.User;
import com.orleave.service.MatchingService;
import com.orleave.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageController {
	
	private final SimpMessageSendingOperations sendingOperations;
	
	@Autowired
	MatchingService matchingService;
	
	@MessageMapping("/match")
	@SendTo("/sub/match")
	public MatchingSendInfo matchingFounded(MatchingReceiveInfo message) throws Exception {
		MatchingSendInfo sendInfo = new MatchingSendInfo();
		sendInfo.setFemale(matchingService.getUserInfo(message.getFemaleNo()));
		sendInfo.setMaleNo(message.getMaleNo());
		sendInfo.setRoomId(message.getRoomId());		
		return sendInfo;
	}
	
	@MessageMapping("/chat/{roomId}")
	public void matchingFounded(@DestinationVariable("roomId") String roomId, Chat chat) throws Exception {
		sendingOperations.convertAndSend("/sub/chat/" + roomId, chat);
	}
	
}
