package com.orleave.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.orleave.dto.message.Chat;
import com.orleave.dto.message.MatchingInfo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageController {
	
	private final SimpMessageSendingOperations sendingOperations;
	
	@MessageMapping("/match")
	@SendTo("/sub/match")
	public MatchingInfo matchingFounded(MatchingInfo message) throws Exception {
		return message;
	}
	
	@MessageMapping("/chat/{roomId}")
	public void matchingFounded(@DestinationVariable("roomId") String roomId, Chat chat) throws Exception {
		sendingOperations.convertAndSend("/sub/chat/" + roomId, chat);
	}
	
}
