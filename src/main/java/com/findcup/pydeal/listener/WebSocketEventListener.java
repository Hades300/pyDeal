package com.findcup.pydeal.listener;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.findcup.pydeal.entity.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import jdk.internal.org.jline.utils.Log;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ConcurrentHashMap<String, LinkedList<String>> userChannelMap;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        logger.info(headerAccessor.getSessionAttributes().toString());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        String channel = (String) headerAccessor.getSessionAttributes().get("channel");
        if(username != null) {
            logger.info("User Disconnected : " + username);

            // Update hash map
            userChannelMap.get(channel).remove(username);

            // Get rid of empty room
            if(userChannelMap.get(channel).size() == 0){
                userChannelMap.remove(channel);
            }
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);

            // Destroy empty room


            messagingTemplate.convertAndSend("/topic"+headerAccessor.getSessionAttributes().get("channel"), chatMessage);
        }
    }
}