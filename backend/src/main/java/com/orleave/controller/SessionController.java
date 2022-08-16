package com.orleave.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.orleave.auth.SsafyUserDetails;
import com.orleave.dto.request.SessionEnterRequestDto;
import com.orleave.dto.request.SessionLeaveRequestDto;
import com.orleave.dto.response.BaseResponseDto;
import com.orleave.dto.response.TokenResponseDto;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.KurentoOptions;

@Api(value = "미팅 세션 API", tags = {"Session"})
@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

	// OpenVidu object as entrypoint of the SDK
	private OpenVidu openVidu;

	// Collection to pair session names and OpenVidu Session objects
	private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
	// Collection to pair session names and tokens (the inner Map pairs tokens and
	// role associated)
	private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

	// URL where our OpenVidu server is listening
	private String OPENVIDU_URL;
	// Secret shared with our OpenVidu server
	private String SECRET;

	public SessionController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
		this.SECRET = secret;
		this.OPENVIDU_URL = openviduUrl;
		this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
	}

	@PostMapping("/enter")
	@ApiOperation(value = "미팅 세션 참가", notes = "미팅 세션을 새로 만들거나 기존에 있던 미팅 세션에 참여하고 이에 맞는 토큰을 반환한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "권한 인증 오류"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> getToken(@RequestBody SessionEnterRequestDto sessionEnterRequestDto, HttpSession httpSession, @ApiIgnore Authentication authentication) {
		if (authentication == null) throw new TokenExpiredException("Token Expired");
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String nickname = userDetails.getUser().getNickname();
		System.out.println("Getting a token from OpenVidu Server | {sessionName}=" + sessionEnterRequestDto.getSessionName());

		String sessionName = sessionEnterRequestDto.getSessionName();

		// Role associated to this user
		OpenViduRole role = OpenViduRole.PUBLISHER;

		// Optional data to be passed to other users when this user connects to the
		// video-call. In this case, a JSON with the value we stored in the HttpSession
		// object on login
		String serverData = "{\"serverData\": \"" + nickname + "\"}";

		// Build connectionProperties object with the serverData and the role
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(serverData).role(role)
				.kurentoOptions(
						new KurentoOptions.Builder().allowedFilters(
								new String[]{"GStreamerFilter","FaceOverlayFilter"}
								).build()
						).build();

		if (this.mapSessions.get(sessionName) != null) {
			// Session already exists
			System.out.println("Existing session " + sessionName);
			try {

				// Generate a new Connection with the recently created connectionProperties
				String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();

				// Update our collection storing the new token
				this.mapSessionNamesTokens.get(sessionName).put(token, role);

				// Prepare the response with the token

				// Return the response to the client
				return ResponseEntity.status(200).body(TokenResponseDto.of(200, "Success", token));
			} catch (OpenViduJavaClientException e1) {
				// If internal error generate an error message and return it to client
				System.out.println(e1);
				return ResponseEntity.status(500).body(BaseResponseDto.of(500, "Internal Server Error"));
			} catch (OpenViduHttpException e2) {
				if (404 == e2.getStatus()) {
					// Invalid sessionId (user left unexpectedly). Session object is not valid
					// anymore. Clean collections and continue as new session
					this.mapSessions.remove(sessionName);
					this.mapSessionNamesTokens.remove(sessionName);
				}
			}
		}

		// New session
		System.out.println("New session " + sessionName);
		try {

			// Create a new OpenVidu Session
			Session session = this.openVidu.createSession();
			// Generate a new Connection with the recently created connectionProperties
			String token = session.createConnection(connectionProperties).getToken();
			// Store the session and the token in our collections
			this.mapSessions.put(sessionName, session);
			this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
			this.mapSessionNamesTokens.get(sessionName).put(token, role);

			// Prepare the response with the token
			// Return the response to the client
			return ResponseEntity.status(200).body(TokenResponseDto.of(200, "Success", token));
			
		} catch (Exception e) {
			// If error generate an error message and return it to client
			return ResponseEntity.status(500).body(BaseResponseDto.of(500, "Internal Server Error"));
		}
	}

	@PostMapping("/leave")
	@ApiOperation(value = "미팅 세션 종료", notes = "참여 중인 미팅 세션을 종료한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "권한 인증 오류 및 세션 토큰 오류"),
        @ApiResponse(code = 404, message = "참여 중인 세션 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<BaseResponseDto> removeUser(@RequestBody SessionLeaveRequestDto sessionLeaveRequestDto, 
			HttpSession httpSession, @ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) throw new TokenExpiredException("Token Expired");
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String nickname = userDetails.getUser().getNickname();
		System.out.println("Removing user | sessionName = " + sessionLeaveRequestDto.getSessionName() + ", token = " + sessionLeaveRequestDto.getToken());

		// Retrieve the params from BODY
		String sessionName = sessionLeaveRequestDto.getSessionName();
		String token = sessionLeaveRequestDto.getToken();

		// If the session exists
		if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {

			// If the token exists
			if (this.mapSessionNamesTokens.get(sessionName).remove(token) != null) {
				// User left the session
				if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
					// Last user left: session must be removed
					this.mapSessions.remove(sessionName);
				}
				return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
			} else {
				// The TOKEN wasn't valid
				System.out.println("Problems in the app server: the TOKEN wasn't valid");
				return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Invalid Token"));
			}

		} else {
			// The SESSION does not exist
			System.out.println("Problems in the app server: the SESSION does not exist");
			return ResponseEntity.status(404).body(BaseResponseDto.of(404, "Session Not Exist"));
		}
	}
}
