package com.chatbox.api;

import com.chatbox.dto.request.SearchUserForm;
import com.chatbox.dto.response.ResponseMessage;
import com.chatbox.dto.response.UserGroupResponse;
import com.chatbox.model.User;
import com.chatbox.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/user")
@CrossOrigin
public class UserAPI {
    private static final Logger logger = LoggerFactory.getLogger(UserAPI.class);

    @Autowired
    private IUserService userService;

    @PostMapping("/search")
    public ResponseEntity<ResponseMessage> searchUser(@Valid @RequestBody SearchUserForm searchUserForm) {
        List<User> users = new ArrayList<>();
        switch (searchUserForm.getType()) {
            case "USERNAME":
                users = userService.findByUsernameIgnoreCaseContaining(searchUserForm.getContent());
                break;
            case "NAME":
                users = userService.findByNameIgnoreCaseContaining(searchUserForm.getContent());
                break;
            default: // ALL
                users = userService.findByUsernameIgnoreCaseContainingOrNameIgnoreCaseContaining(
                        searchUserForm.getContent(), searchUserForm.getContent());
                break;
        }
        List<UserGroupResponse> userGroupResponses = users.stream().map(user -> {
            return new UserGroupResponse(
                    user.getId(),
                    user.getName(),
                    user.getAvatar()
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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getUserProfile(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        return user.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseMessage(
                                ResponseMessage.ResponseType.OK,
                                "Data user has already taken successfully!",
                                user
                        )) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseMessage(
                                ResponseMessage.ResponseType.FAILED,
                                "Can't find user with id = " + id,
                                ""
                        ));
    }

    @PostMapping("")
    public ResponseEntity<?> updateUserProfile(@RequestBody User user) {
        Optional<User> userDBOptional = userService.findById(user.getId());
        if (userDBOptional.isPresent()) {
            User userDB = userDBOptional.get();
            if (userService.findByUsernameAndIdNot(user.getUsername(), user.getId()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseMessage(
                                ResponseMessage.ResponseType.FAILED,
                                "Can't set username because it has been already used with: " + user.getUsername(),
                                ""
                        )
                );
            }
            // Email and phone do the same username

            userDB.setName(user.getName());
            userDB.setUsername(user.getUsername());
            userDB.setEmail(user.getEmail());
            userDB.setPhone(user.getPhone());
            userDB.setBirthday(user.getBirthday());
            userDB.setGender(user.getGender());
            logger.info("Update data: " + userService.save(userDB));

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseMessage(
                            ResponseMessage.ResponseType.OK,
                            "Update user successfully.",
                            userDB
                    )
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseMessage(
                        ResponseMessage.ResponseType.FAILED,
                        "Can't find user with id = " + user.getId(),
                        ""
                )
        );
    }

    @GetMapping("/favourite")
    public ResponseEntity<ResponseMessage> findFavouriteUser(@RequestParam Long currentUserId,
                                                             @RequestParam Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseMessage(
                        ResponseMessage.ResponseType.OK,
                        "Data favourite has been taken successfully.",
                        userService.isFavouriteUser(currentUserId, userId)
                )
        );
    }

    @GetMapping("/groups-code")
    public ResponseEntity<ResponseMessage> findCodeByUserId() {
        Long currentId = 1L;
        return ResponseEntity.ok(
                new ResponseMessage(
                        ResponseMessage.ResponseType.OK,
                        "Group's code already taken successfully.",
                        userService.findCodeByUserId(currentId)
                )
        );
    }
}
