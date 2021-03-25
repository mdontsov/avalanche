package com.avalanchelabs.app;

import com.avalanchelabs.app.dto.UserDTO;
import com.avalanchelabs.app.entity.User;
import com.avalanchelabs.app.json.Address;
import com.avalanchelabs.app.repository.RoleRepo;
import com.avalanchelabs.app.repository.UserRepo;
import com.avalanchelabs.app.service.UserService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppApplicationTests {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserService userService;

    @Test
    void userRepositoryIsNotNull() {

        User user = new User();
        user.setUsername("Maksim");
        user.setPassword("123456789");
        user.setRole(roleRepo.findByName("ROLE_ADMIN"));
        userRepo.save(user);
        Assert.notNull(userRepo.findByUsername("Maksim"));
    }

    @Test
    void userEntityToUserDTO() {

        User user = new User();
        Address address = new Address();
        user.setUsername("Maksim");
        user.setPassword("123456789");
        address.setCityName("Tallinn");
        address.setStreetName("Nisu");
        address.setPostalCode(10317);
        user.setAddress(address);
        user.setRole(roleRepo.findByName("ROLE_ADMIN"));
        userRepo.save(user);
        Object[] object = userService.modelMapper().map(userService.findUsers(), UserDTO[].class);
        for (Object obj : object) {
            Assert.isTrue(obj instanceof UserDTO);
        }
    }

}
