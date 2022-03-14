package com.chatbox.service.impl;

import com.chatbox.dto.response.UserMessageResponse;
import com.chatbox.model.Group;
import com.chatbox.model.Message;
import com.chatbox.model.User;
import com.chatbox.repositories.IGroupRepository;
import com.chatbox.repositories.IMessageRepository;
import com.chatbox.repositories.IUserRepository;
import com.chatbox.service.IMessageService;
import com.chatbox.service.IUserService;
import com.chatbox.utils.FormatTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private IMessageRepository messageRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IGroupRepository groupRepository;

    @Autowired
    private IUserService userService;

    @Override
    public Message save(Message message) {
        if (message.getTime() == null) {
            message.setTime(new Date());
        }
        if (message.getType() == null) {
            message.setType(Message.MessageType.CHAT);
        }
        if (message.getRead() == null) {
            message.setRead(false);
        }
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessage(Long senderId, Long recipientId) {
        return messageRepository.getMessage(senderId, recipientId);
    }

    @Override
    public List<Message> getMessage(Long groupId) {
        return messageRepository.getMessage(groupId, "GROUP");
    }

    @Override
    public Message recallMessage(Long id) {
        Message message = messageRepository.getById(id);
        message.setHasRecalled(1);
        message.setContent("Message has been recalled.");
        return save(message);
    }

    @Override
    public Message readMessage(Long id) {
        Message message = messageRepository.getById(id);
        message.setRead(true);
        return save(message);
    }

    @Override
    public List<UserMessageResponse> getAllMessageOfUser(Long id) {
        List<Message> messages = messageRepository.getAllMessageOfUser(id, userRepository.findGroupIdByUserId(id));
        Set<Long> idsUser = new HashSet<>();
        Set<Long> idsGroup = new HashSet<>();
        List<UserMessageResponse> userMessageResponses = new ArrayList<>();
        messages.stream().map(message -> {
            if (message.getSendToType().equals(Message.SendToType.ONLY.name())) {
                Long idUser = message.getRecipientId() != id ? message.getRecipientId() : message.getSenderId();
                if (!idsUser.contains(idUser)) {
                    idsUser.add(idUser);
                    User user = userRepository.getById(idUser);
                    return new UserMessageResponse(
                            user.getId(), user.getName(), user.getAvatar(), !message.getRead(),
                            message.getSenderId() == id, userService.isFavouriteUser(id, idUser), false,
                            message.getContent(), FormatTimeUtils.convertTime(message.getTime())
                    );
                }
            }
            else if (message.getSendToType().equals(Message.SendToType.GROUP.name())) {
                Long idGroup = message.getRecipientId();
                if (!idsGroup.contains(idGroup)) {
                    idsGroup.add(idGroup);
                    Group group = groupRepository.getById(idGroup);
                    return new UserMessageResponse(
                            group.getId(), group.getName(), group.getAvatar(), !message.getRead(),
                            message.getSenderId() == id, false, true,
                            message.getContent(), FormatTimeUtils.convertTime(message.getTime()),
                            group.getCode(), userRepository.getById(message.getSenderId()).getName()
                    );
                }
            }
            return null;
        }).forEachOrdered(userMessageResponse -> {
            if (userMessageResponse != null) {
                userMessageResponses.add(userMessageResponse);
            }
        });
        return userMessageResponses;
    }
}
