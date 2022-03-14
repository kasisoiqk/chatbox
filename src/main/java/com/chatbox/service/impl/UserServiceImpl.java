package com.chatbox.service.impl;

import com.chatbox.model.Message;
import com.chatbox.model.User;
import com.chatbox.repositories.IMessageRepository;
import com.chatbox.repositories.IUserRepository;
import com.chatbox.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IMessageRepository messageRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByUsernameAndIdNot(String username, Long id) {
        return userRepository.findByUsernameAndIdNot(username, id);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public User save(User user) {
        if (user.getAvatar() == null || user.getAvatar().isEmpty()) {
            user.setAvatar("user-default.jpg");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getUsername());
        }
        if (user.getBirthday() == null) {
            user.setBirthday(new Date());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findByUsernameIgnoreCaseContaining(String username) {
        return userRepository.findByUsernameIgnoreCaseContaining(username);
    }

    @Override
    public List<User> findByNameIgnoreCaseContaining(String name) {
        return userRepository.findByNameIgnoreCaseContaining(name);
    }

    @Override
    public List<User> findByUsernameIgnoreCaseContainingOrNameIgnoreCaseContaining(String username, String name) {
        return userRepository.findByUsernameIgnoreCaseContainingOrNameIgnoreCaseContaining(username, name);
    }

    @Override
    public Boolean isFavouriteUser(Long idCurrentUser, Long idUser) {
        final int NUMBER_MESSAGE = 100;
        List<Message> messages = messageRepository.getMessage(idCurrentUser, idUser);
        return messages.size() >= NUMBER_MESSAGE;
    }

    @Override
    public List<String> findCodeByUserId(Long userId) {
        return userRepository.findCodeByUserId(userId);
    }
}
