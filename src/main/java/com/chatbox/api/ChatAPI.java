package com.chatbox.api;

import com.chatbox.dto.response.ChatResponse;
import com.chatbox.dto.response.NotifyResponse;
import com.chatbox.model.Group;
import com.chatbox.model.Message;
import com.chatbox.service.IGroupService;
import com.chatbox.service.IMessageService;
import com.chatbox.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Controller
@CrossOrigin
public class ChatAPI {
    private static final Logger logger = LoggerFactory.getLogger(ChatAPI.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IGroupService groupService;

    @MessageMapping("/messages/chat.sendMessage/{toUserId}")
    public void sendMessage(@DestinationVariable String toUserId, Message message) {
        logger.info("Handling send message: " + message);
        if (userService.existsById(message.getSenderId()) && userService.existsById(message.getRecipientId())) {
            message.setId(0L);
            message = messageService.save(message);
            ChatResponse chatResponse = new ChatResponse(
                    message.getId(), message.getType(), message.getSendToType(), message.getContent(), message.getHasRecalled(),
                    message.getTime(), message.getSenderId(), message.getRecipientId()
            );
            simpMessagingTemplate.convertAndSend("/topic/messages/" + toUserId,
                    new NotifyResponse(NotifyResponse.ResponseType.MESSAGE, chatResponse));
            simpMessagingTemplate.convertAndSend("/topic/messages/" + message.getSenderId(),
                    new NotifyResponse(NotifyResponse.ResponseType.MESSAGE, chatResponse));
        } else {
            logger.info("Can't send message.");
        }
    }

    @MessageMapping("/messages/chat.recallMessage/{toUserId}")
    public void recallMessage(@DestinationVariable String toUserId, Long idMessage) {
        logger.info("Handling recall message with id: " + idMessage);

        Message message = messageService.recallMessage(idMessage);
        ChatResponse chatResponse = new ChatResponse(
                message.getId(), message.getType(), message.getSendToType(), message.getContent(), message.getHasRecalled(),
                message.getTime(), message.getSenderId(), message.getRecipientId()
        );
        if (message.getSendToType().equals(Message.SendToType.ONLY.name())) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + toUserId,
                    new NotifyResponse(NotifyResponse.ResponseType.MESSAGE, chatResponse));
        }
        else {
            Optional<Group> group = groupService.findById(message.getRecipientId());
            if (group.isPresent()) {
                simpMessagingTemplate.convertAndSend("/topic/messages/groups/" + group.get().getCode(),
                        new NotifyResponse(NotifyResponse.ResponseType.MESSAGE, chatResponse));
            }
        }
    }

    @MessageMapping("/messages/chat.readMessage")
    public void readMessage(Long idMessage) {
        logger.info("Handling read message with id: " + idMessage);
        Message message = messageService.readMessage(idMessage);
    }

}
