package com.avalanchelabs.app.service;

import com.avalanchelabs.app.entity.Role;
import com.avalanchelabs.app.entity.User;
import com.avalanchelabs.app.repository.RoleRepo;
import com.avalanchelabs.app.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    public UserService(@Lazy PasswordEncoder passwordEncoder, @Lazy RoleRepo roleRepo, @Lazy UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }

    public void saveUser(User user) {
        Role userRole = roleRepo.findByName("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public User findByUserName(String userName) {
        return userRepo.findByUsername(userName);
    }

    public List<User> findUsers() {
        return userRepo.findAll();
    }

    public List<Role> findRoles() {
        return roleRepo.findAll();
    }

    public User findByUserNameAndPassword(String userName, String password) {
        User user = findByUserName(userName);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
