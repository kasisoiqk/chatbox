package com.chatbox.api;

import com.chatbox.dto.request.SignInForm;
import com.chatbox.dto.request.SignUpForm;
import com.chatbox.dto.response.JwtResponse;
import com.chatbox.dto.response.ResponseMessage;
import com.chatbox.model.Role;
import com.chatbox.model.User;
import com.chatbox.security.jwt.JwtProvider;
import com.chatbox.security.userprincal.UserPrinciple;
import com.chatbox.service.IRoleService;
import com.chatbox.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthAPI {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("")
    public ResponseEntity<ResponseMessage> getAllUsers() {
        return ResponseEntity.ok(new ResponseMessage(
                ResponseMessage.ResponseType.OK,
                "All users has taken successfully!",
                userService.findAll()
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(
                    new ResponseMessage(
                            ResponseMessage.ResponseType.FAILED,
                            "The username existed! Please try again.",
                            ""
                    ), HttpStatus.NOT_IMPLEMENTED);
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(
                    new ResponseMessage(
                            ResponseMessage.ResponseType.FAILED,
                            "The email existed! Please try again.",
                            ""
                    ), HttpStatus.NOT_IMPLEMENTED);
        }
        User user = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(),
                passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(Role.RoleName.ADMIN.name()).orElseThrow(
                            () -> new RuntimeException("Role not found.")
                    );
                    roles.add(adminRole);
                    break;
                default:
                    Role userRole = roleService.findByName(Role.RoleName.USER.name()).orElseThrow(
                            () -> new RuntimeException("Role not found.")
                    );
                    roles.add(userRole);
                    break;
            }
        });
        user.setRoles(roles);
        user = userService.save(user);
        return new ResponseEntity<>(
                new ResponseMessage(
                        ResponseMessage.ResponseType.OK,
                        "Create user successfully!",
                        ""
                ), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseMessage> login(@Valid @RequestBody SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new ResponseMessage(
                ResponseMessage.ResponseType.OK,
                "Login successfully!",
                new JwtResponse(
                        userPrinciple.getId(),
                        token,
                        userPrinciple.getName(),
                        userPrinciple.getAvatar(),
                        userPrinciple.getAuthorities()
                )
        ));
    }

}
