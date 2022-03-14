package com.chatbox.service;

import com.chatbox.dto.request.CreateGroupForm;
import com.chatbox.model.Group;
import com.chatbox.model.User;

import java.util.List;
import java.util.Optional;

public interface IGroupService {
    Group save(Group group);
    Optional<Group> findById(Long id);
    List<Group> findAll();
    Long getNumberMember(Long groupId);
    List<Group> findByNameIgnoreCaseContaining(String name);
    List<Group> findByCode(String code);
    Long findIdByCode(String code);
    List<Group> findByNameIgnoreCaseContainingOrCode(String name, String code);
    Group createGroup(CreateGroupForm createGroupForm);
    List<User> getGroupsMember(Long groupId);
    Boolean addMember(Long groupId, Long memberId);
    Boolean removeMember(Long groupId, Long memberId);
    Boolean existsByName(String name);
    Boolean existsById(Long id);
}
