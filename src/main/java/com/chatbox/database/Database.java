package com.chatbox.database;

import com.chatbox.model.Role;
import com.chatbox.model.User;
import com.chatbox.repositories.IRoleRepository;
import com.chatbox.repositories.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDatabase(IRoleRepository IRoleRepository, IUserRepository IUserRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Role adminRole = new Role(Role.RoleName.ADMIN.name());
                Role userRole = new Role(Role.RoleName.USER.name());
                User userA = new User("I'MKAI", "quockhanh", "quockhanh01091@gmail.com", passwordEncoder.encode("123456"));
                User userC = new User("Quoc Khanh", "qkhanh", "quockhanh@gmail.com", passwordEncoder.encode("123456"));
                User userB = new User("Thùy Duyên", "thuyduyen", "thuyduyen@gmail.com", passwordEncoder.encode("123456"));
                userA.setRoles(new HashSet<>(Arrays.asList(userRole)));
                userA.setAvatar("user.jpg");
                userA.setBirthday(new Date());
                userB.setAvatar("user2.jpg");
                userC.setAvatar("user1.jpg");
//                User userB = new User("ADMIN", "admin", "admin@gmail.com", "123456");
//                userB.setRoles(new HashSet<>(Arrays.asList(adminRole)));

                logger.info("Insert data: " + IRoleRepository.save(adminRole));
                logger.info("Insert data: " + IRoleRepository.save(userRole));
                logger.info("Insert data: " + IUserRepository.save(userA));
                logger.info("Insert data: " + IUserRepository.save(userB));
                logger.info("Insert data: " + IUserRepository.save(userC));
            }
        };
    }
}
