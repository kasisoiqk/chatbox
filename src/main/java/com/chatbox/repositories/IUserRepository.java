package com.chatbox.repositories;

import com.chatbox.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByUsernameAndIdNot(String username, Long id);
    List<User> findByUsernameIgnoreCaseContaining(String username);
    List<User> findByNameIgnoreCaseContaining(String name);
    List<User> findByUsernameIgnoreCaseContainingOrNameIgnoreCaseContaining(String username, String name);

    @Query("select u from user u, groups_user g where u.id = g.userId and g.groupId = ?1")
    List<User> getGroupsMember(Long groupId);

    @Query("select g.code from groups g, groups_user gu where g.id = gu.groupId and gu.userId = ?1")
    List<String> findCodeByUserId(Long userId);

    @Query("select g.groupId from groups_user g where g.userId = ?1")
    List<Long> findGroupIdByUserId(Long userId);

//    @Query(value = "select u.* from (select count(m.recipientId) numbersend, m.recipientId from message m " +
//            "where m.senderId = ?1 group by m.recipientId having numbersend > ?2 order by numbersend desc) a" +
//            ", user u where a.recipientId = u.id", nativeQuery = true)
//    List<User> findFavouriteUser(Long id, int NUMBER_MESSAGE);
}
