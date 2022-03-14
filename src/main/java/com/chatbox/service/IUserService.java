package com.chatbox.service;

import com.chatbox.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long id);
    List<User> findAll();
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndIdNot(String username, Long id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsById(Long id);
    User save(User user);
    List<User> findByUsernameIgnoreCaseContaining(String username);
    List<User> findByNameIgnoreCaseContaining(String name);
    List<User> findByUsernameIgnoreCaseContainingOrNameIgnoreCaseContaining(String username, String name);
    Boolean isFavouriteUser(Long idCurrentUser, Long idUser);
    List<String> findCodeByUserId(Long userId);
}
