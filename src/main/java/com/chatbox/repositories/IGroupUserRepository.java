package com.chatbox.repositories;

import com.chatbox.model.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupUserRepository extends JpaRepository<GroupUser, Long> {
    Long countByGroupId(Long groupId);
    void deleteByGroupIdAndUserId(Long groupId, Long userId);
}
