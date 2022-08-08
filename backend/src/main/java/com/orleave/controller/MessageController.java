package com.orleave.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.orleave.dto.message.MatchingInfo;

@Controller
public class MessageController {
	
	@MessageMapping("/match")
	@SendTo("/sub/match")
	public MatchingInfo matchingFounded(MatchingInfo message) throws Exception {
		return message;
	}
	
}
