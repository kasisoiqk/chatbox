package com.chatbox.service;

import com.chatbox.dto.response.UserMessageResponse;
import com.chatbox.model.Message;

import java.util.List;
import java.util.Optional;

public interface IMessageService {
    Message save(Message message);
    List<Message> getMessage(Long senderId, Long recipientId);
    List<Message> getMessage(Long groupId);
    Message recallMessage(Long id);
    Message readMessage(Long id);
    List<UserMessageResponse> getAllMessageOfUser(Long id);
}
