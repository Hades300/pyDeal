package com.findcup.pydeal.controller;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.findcup.pydeal.entity.ChatMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    
    @Autowired
    // Substitute HashMap to ConcurrentHashMap to avoid multi-thread conflict.
    private ConcurrentHashMap<String, LinkedList<String>> userChannelMap;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat.sendPublicMessage")
    public void sendPublicMessage(@Payload ChatMessage chatMessage) {
        System.out.println("chat.sendPublicMessage");
        System.out.println(chatMessage);
        messagingTemplate.convertAndSend("/topic"+chatMessage.getChannel(), chatMessage);
    }

    @MessageMapping("/chat.sendPrivateMessage")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
        System.out.println("chat.sendPrivateMessage");
        System.out.println(chatMessage);
        messagingTemplate.convertAndSendToUser(chatMessage.getChannel(), "/private", chatMessage);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("Chat.addUser");
        System.out.println(chatMessage);
        // Add username and channel to web socket session (for disconnect operations)
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("channel", chatMessage.getChannel());
        // Classify users by the topic(channel) they selected
        String channel = chatMessage.getChannel();
        if(!userChannelMap.containsKey(channel)){
            userChannelMap.put(chatMessage.getChannel(), new LinkedList<String>());
        }
        userChannelMap.get(channel).add(chatMessage.getSender());
        // Ready to return all users of one room
        chatMessage.setContent(userChannelMap.get(chatMessage.getChannel()).toString());
        // Send message to Client
        messagingTemplate.convertAndSend("/topic"+chatMessage.getChannel(), chatMessage);
    }
}