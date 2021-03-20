package com.avalanchelabs.app.service;

import com.avalanchelabs.app.entity.Role;
import com.avalanchelabs.app.entity.User;
import com.avalanchelabs.app.repository.RoleRepo;
import com.avalanchelabs.app.repository.UserRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(RoleRepo roleRepo, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        Role userRole = roleRepo.findByRoleName("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    public User findByUserNameAndPassword(String userName, String password) {
        User user = findByUserName(userName);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
