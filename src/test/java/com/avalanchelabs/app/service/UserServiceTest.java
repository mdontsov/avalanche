package com.avalanchelabs.app.service;

import com.avalanchelabs.app.entity.Role;
import com.avalanchelabs.app.entity.User;
import com.avalanchelabs.app.repository.RoleRepo;
import com.avalanchelabs.app.repository.UserRepo;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@SuppressWarnings("squid:S2699")
class UserServiceTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    void modelMapperTest() {
        ModelMapper mapper = userService.modelMapper();
        Assert.isInstanceOf(mapper.getClass(), userService.modelMapper());
    }

    @Test
    void saveUserTest() {
        User user = new User();
        Role userRole = roleRepo.findByName("ROLE_ADMIN");
        user.setUsername("Maksim");
        user.setRole(userRole);
        user.setPassword(encoder.encode("123456789"));
        user = userRepo.save(user);
        Assert.notNull(user);
    }

    @Test
    void findByUserName() {
    }

    @Test
    void findUsers() {
    }

    @Test
    void findRoles() {
    }

    @Test
    void findByUserNameAndPassword() {
    }
}