package com.chatbox.api;

import com.chatbox.dto.request.CreateGroupForm;
import com.chatbox.dto.response.ChatResponse;
import com.chatbox.dto.response.ResponseMessage;
import com.chatbox.dto.response.UserMessageResponse;
import com.chatbox.model.Group;
import com.chatbox.model.Message;
import com.chatbox.repositories.IMessageRepository;
import com.chatbox.service.IGroupService;
import com.chatbox.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@CrossOrigin
public class MessageAPI {
    private final static Logger logger = LoggerFactory.getLogger(MessageAPI.class);

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    IMessageRepository messageRepository;

    @PostMapping("/test")
    public Group test(@RequestBody CreateGroupForm createGroupForm) {
        return groupService.createGroup(createGroupForm);
    }

    @GetMapping("")
    public ResponseEntity<ResponseMessage> getMessage(@RequestParam(required = true) Long senderId,
                                                          @RequestParam(required = false, defaultValue = "0") Long recipientId,
                                                          @RequestParam(required = false) String groupCode,
                                                          @RequestParam(defaultValue = "ONLY") String sendToType) {
        List<Message> messages = new ArrayList<>();
        if (sendToType.equals("ONLY")) {
            messages = messageService.getMessage(senderId, recipientId);
        }
        else if (sendToType.equals("GROUP")) {
            Long groupId = groupService.findIdByCode(groupCode);
            messages = messageService.getMessage(groupId);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseMessage(
                            ResponseMessage.ResponseType.FAILED,
                            "sendToType must be 'ONLY' or 'GROUP'",
                            ""
                    )
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseMessage(
                        ResponseMessage.ResponseType.OK,
                        "Data message has been taken successfully.",
                        messages.stream().map(message -> {
                            return new ChatResponse(
                                    message.getId(), message.getType(), message.getSendToType(),
                                    message.getContent(), message.getHasRecalled(),
                                    message.getTime(), message.getSenderId(), message.getRecipientId()
                            );
                        })
                )
        );
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<ResponseMessage> getAllMessageOfUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseMessage(
                        ResponseMessage.ResponseType.OK,
                        "Data user-message has been taken successfully.",
                        messageService.getAllMessageOfUser(id)
                )
        );
    }

    @PostMapping("")
    public ResponseEntity<ResponseMessage> sendMessage(@Valid @RequestBody Message message) {
        message.setId(0L);
        message = messageService.save(message);
        logger.info("Insert data: " + message);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseMessage(
                        ResponseMessage.ResponseType.OK,
                        "Message has been sent successfully.",
                        message
                )
        );
    }

    @PostMapping("/recall/{id}")
    public ResponseEntity<ResponseMessage> recallMessage(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseMessage(
                        ResponseMessage.ResponseType.OK,
                        "Message has been recalled successfully.",
                        messageService.recallMessage(id)
                )
        );
    }
}
