package com.human.chatapplication.controller;

import com.human.chatapplication.entity.ChatMessage;
import com.human.chatapplication.enums.MessageType;
import com.human.chatapplication.services.KafkaProducer;
import com.human.chatapplication.storage.MessageStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka/chat")
public class ChatController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private MessageStorage messageStorage;

    @GetMapping(value = "/sendMessage")
    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessage message) throws Exception {
        LOGGER.debug("ChatController.sendMessage : Received message from Web Browser using STOMP Client and further sending it to a KAFKA Topic");
        kafkaProducer.send(ChatMessage.MessageType.valueOf(message.getType().name()) + "-" + message.getContent() + "-"
                + message.getSender());
    }

    @MessageMapping("/chat.addUser")
    @SendTo("topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("userName", chatMessage.getSender());
        return chatMessage;
    }

    @GetMapping(value = "/consume")
    public String getAllReceiveMessage(){
        String message = messageStorage.toString();
        messageStorage.clear();
        return message;
    }
}
