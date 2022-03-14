package com.chatbox.api;

import com.chatbox.dto.request.CreateGroupForm;
import com.chatbox.dto.response.ChatResponse;
import com.chatbox.dto.response.NotifyResponse;
import com.chatbox.model.Group;
import com.chatbox.model.Message;
import com.chatbox.model.User;
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

import java.util.List;

@Controller
@CrossOrigin
public class ChatGroupAPI {
    private static final Logger logger = LoggerFactory.getLogger(ChatGroupAPI.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IGroupService groupService;

    @MessageMapping("/messages/groups/chat.createGroup")
    public void createGroup(CreateGroupForm createGroupForm) {
        logger.info("Handling create new group: " + createGroupForm);

        Long currentId = 1L;
        if (groupService.existsByName(createGroupForm.getName())) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + currentId,
                    new NotifyResponse(
                            NotifyResponse.ResponseType.GROUP,
                            "Group name already used: " + createGroupForm.getName() + "! Please rename and try again."
                    ));
            return;
        }

        Group group = groupService.createGroup(createGroupForm);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + currentId,
                new NotifyResponse(
                        NotifyResponse.ResponseType.CREATED_GROUP,
                        group
                ));
        List<User> users = groupService.getGroupsMember(group.getId());
        users.forEach(user -> {
            if (user.getId() != currentId) {
                simpMessagingTemplate.convertAndSend("/topic/messages/" + user.getId(),
                        new NotifyResponse(
                                NotifyResponse.ResponseType.GROUP,
                                "You have just been added to '" + group.getName() + "' group by " +
                                        userService.findById(currentId).get().getName() + ". Code: " + group.getCode()
                        ));
            }
        });
    }

    @MessageMapping("/messages/groups/chat.sendMessage/{groupCode}")
    public void sendMessage(@DestinationVariable String groupCode, Message message) {
        logger.info("Handling send message to group: " + message);
        if (userService.existsById(message.getSenderId()) && groupService.existsById(message.getRecipientId())) {
            message.setId(0L);
            message = messageService.save(message);
            ChatResponse chatResponse = new ChatResponse(
                    message.getId(), message.getType(), message.getSendToType(), message.getContent(), message.getHasRecalled(),
                    message.getTime(), message.getSenderId(), message.getRecipientId()
            );
            simpMessagingTemplate.convertAndSend("/topic/messages/groups/" + groupCode,
                    new NotifyResponse(NotifyResponse.ResponseType.MESSAGE, chatResponse));
        } else {
            logger.info("Can't send message.");
        }
    }
}
