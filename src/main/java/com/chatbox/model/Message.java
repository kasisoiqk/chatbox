package com.chatbox.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "message")
@Table(name = "message")
public class Message {
    public enum MessageType {
        JOIN,
        LEAVE,
        CHAT
    }

    public enum SendToType {
        GROUP,
        ONLY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String type;

    @Column(length = 20)
    private String sendToType;

    @NotEmpty
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;
    private int hasRecalled; // 1: true ; 0: false
    private Date time;
    private Long senderId;
    private Long recipientId;
    private Boolean isRead;

    public Message() {
    }

    public Message(Long id, String type, String sendToType, String content, int hasRecalled, Date time,
                   Long senderId, Long recipientId, Boolean isRead) {
        this.id = id;
        this.type = type;
        this.sendToType = sendToType;
        this.content = content;
        this.hasRecalled = hasRecalled;
        this.time = time;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type.name();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSendToType() {
        return sendToType;
    }

    public void setSendToType(String sendToType) {
        this.sendToType = sendToType;
    }

    public int getHasRecalled() {
        return hasRecalled;
    }

    public void setHasRecalled(int hasRecalled) {
        this.hasRecalled = hasRecalled;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", sendToType='" + sendToType + '\'' +
                ", content='" + content + '\'' +
                ", hasRecalled=" + hasRecalled +
                ", time=" + time +
                ", senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", isRead=" + isRead +
                '}';
    }
}
