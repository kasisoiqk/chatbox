package com.chatbox.api;

import com.chatbox.dto.request.CreateGroupForm;
import com.chatbox.dto.request.SearchUserForm;
import com.chatbox.dto.response.ResponseMessage;
import com.chatbox.dto.response.UserGroupResponse;
import com.chatbox.model.Group;
import com.chatbox.model.User;
import com.chatbox.repositories.IGroupRepository;
import com.chatbox.repositories.IUserRepository;
import com.chatbox.service.IGroupService;
import com.chatbox.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/group")
@CrossOrigin
public class GroupAPI {
    @Autowired
    private IGroupService groupService;

    @PostMapping("")
    public ResponseEntity<ResponseMessage> createGroup(@RequestBody CreateGroupForm createGroupForm) {
        return ResponseEntity.ok(
                new ResponseMessage(
                        ResponseMessage.ResponseType.OK,
                        "Create group successfully.",
                        groupService.createGroup(createGroupForm)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getGroup(@PathVariable Long id) {
        Optional<Group> groupOptional = groupService.findById(id);
        return groupOptional.isPresent() ?
                ResponseEntity.ok(
                        new ResponseMessage(
                                ResponseMessage.ResponseType.OK,
                                "Group profile already taken successfully.",
                                groupOptional.get()
                        )
                ):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseMessage(
                                ResponseMessage.ResponseType.FAILED,
                                "Could not find group with id = " + id,
                                ""
                        )
                );
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ResponseMessage> getGroupByCode(@PathVariable String code) {
        Long id = groupService.findIdByCode(code.toUpperCase());
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseMessage(
                            ResponseMessage.ResponseType.FAILED,
                            "Could not find group with code = " + code,
                            ""
                    )
            );
        }
        return getGroup(id);
    }

    @PostMapping("/search")
    public ResponseEntity<ResponseMessage> searchGroup(@Valid @RequestBody SearchUserForm searchUserForm) {
        List<Group> groups = new ArrayList<>();
        switch (searchUserForm.getType()) {
            case "NAME":
                groups = groupService.findByNameIgnoreCaseContaining(searchUserForm.getContent());
                break;
            case "CODE":
                groups = groupService.findByCode(searchUserForm.getContent());
                break;
            default:
                groups = groupService.findByNameIgnoreCaseContainingOrCode(
                        searchUserForm.getContent(), searchUserForm.getContent());
        }
        List<UserGroupResponse> userGroupResponses = groups.stream().map(group -> {
            return new UserGroupResponse(
                    group.getId(),
                    group.getName(),
                    group.getAvatar()
            );
        }).collect(Collectors.toList());

        if (userGroupResponses.size() <= 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseMessage(
                            ResponseMessage.ResponseType.FAILED,
                            "Can't find user with content = " + searchUserForm.getContent(),
                            ""
                    ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseMessage(
                        ResponseMessage.ResponseType.OK,
                        "Data user has already taken successfully!",
                        userGroupResponses
                ));
    }
}
