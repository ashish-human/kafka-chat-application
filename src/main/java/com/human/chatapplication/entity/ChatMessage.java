package com.human.chatapplication.entity;

import com.human.chatapplication.enums.MessageType;
import org.springframework.stereotype.Component;

@Component
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public ChatMessage() {
        super();
    }

    public ChatMessage(MessageType message, String content, String sender) {
        super();
        this.type = message;
        this.content = content;
        this.sender = sender;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "ChatMessage [type=" + type + ", content=" + content + ", sender=" + sender + "]";
    }


}
