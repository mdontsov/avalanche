package com.avalanchelabs.app;

import com.avalanchelabs.app.configuration.UserDetails;
import com.avalanchelabs.app.configuration.UserDetailsServiceImpl;
import com.avalanchelabs.app.configuration.jwt.JwtFilter;
import com.avalanchelabs.app.configuration.jwt.JwtProvider;
import com.avalanchelabs.app.controller.*;
import com.avalanchelabs.app.dto.RoleDTO;
import com.avalanchelabs.app.dto.UserDTO;
import com.avalanchelabs.app.entity.Role;
import com.avalanchelabs.app.entity.User;
import com.avalanchelabs.app.json.Address;
import com.avalanchelabs.app.repository.RoleRepo;
import com.avalanchelabs.app.repository.UserRepo;
import com.avalanchelabs.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@SuppressWarnings("squid:S2699")
class AppApplicationTests {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtProvider provider;

    @Autowired
    private JwtFilter filter;

    @Autowired
    private UserDetails userDetails;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthController controller;

    @Autowired
    private AuthRequest authRequest;

    @Autowired
    private RegistrationRequest registrationRequest;

    @Autowired
    private TestController testController;

    @Test
    void modelMapper() {
        ModelMapper mapper = userService.modelMapper();
        Assert.isInstanceOf(mapper.getClass(), userService.modelMapper());
    }

    @Test
    void saveUser() {
        User user = new User();
        Role userRole = roleRepo.findByName("ROLE_ADMIN");
        user.setUsername("Maksim");
        user.setRole(userRole);
        user.setPassword(encoder.encode("123456789"));
        user = userRepo.save(user);
        Assert.notNull(user, "Null");
    }

    @Test
    void userNotNull() {
        User user = new User();
        Role userRole = roleRepo.findByName("ROLE_ADMIN");
        user.setUsername("Toomas");
        user.setRole(userRole);
        user.setPassword(encoder.encode("123456789"));
        userService.saveUser(user);
        Assert.notNull(userService.findByUserName(user.getUsername()), "Null");
    }

    @Test
    void findUsers() {
        Assert.isInstanceOf(List.class, userService.findUsers());
    }

    @Test
    void findRoles() {
        Assert.isInstanceOf(List.class, userService.findRoles());
    }

    @Test
    void findByUserNameAndPassword() {
        User user = new User();
        user.setUsername("Maria");
        user.setPassword("Av@1anch3");
        String password = user.getPassword();
        Address address = new Address();
        address.setCityName("Tallinn");
        address.setStreetName("Meistri");
        address.setPostalCode(11111);
        user.setAddress(address);
        userService.saveUser(user);
        Assert.notNull(userService.findByUserNameAndPassword(user.getUsername(), password), "Null");
    }

    @Test
    void userRepositoryIsNotNull() {
        User user = new User();
        user.setUsername("Igor");
        user.setPassword("123456789");
        user.setRole(roleRepo.findByName("ROLE_ADMIN"));
        userRepo.save(user);
        Assert.notNull(userRepo.findByUsername("Igor"), "Null");
    }

    @Test
    void userEntityToUserDTO() {
        User user = new User();
        Address address = new Address();
        user.setUsername("Irina");
        user.setPassword("123456789");
        address.setCityName("Tallinn");
        address.setStreetName("Nisu");
        address.setPostalCode(10317);
        user.setAddress(address);
        user.setRole(roleRepo.findByName("ROLE_ADMIN"));
        userRepo.save(user);
        Object[] object = userService.modelMapper().map(userService.findUsers(), UserDTO[].class);
        for (Object obj : object) {
            Assert.isTrue(obj instanceof UserDTO, "False");
        }
    }

    @Test
    void generateToken() {
        Assert.isInstanceOf(String.class, provider.generateToken("Maksim"));
    }

    @Test
    void validateToken() {
        String token = provider.generateToken("Irina");
        Assert.isTrue(provider.validateToken(token), "False");
    }

    @Test
    void getUsernameFromToken() {
        String token = provider.generateToken("Igor");
        Assert.notNull(provider.getUsernameFromToken(token), "False");
    }

    @Test
    void filterRequest() throws IOException, ServletException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        ServletResponse response = Mockito.mock(ServletResponse.class);
        FilterChain chain = Mockito.mock(FilterChain.class);
        Assert.isInstanceOf(ServletRequest.class, request);
        Assert.isInstanceOf(ServletResponse.class, response);
        Assert.isInstanceOf(FilterChain.class, chain);
        filter.doFilter(request, response, chain);
    }

    @Test
    void userDetailsFromEntity() {
        User user = new User();
        Role userRole = roleRepo.findByName("ROLE_ADMIN");
        user.setUsername("Kristel");
        user.setRole(userRole);
        user.setPassword(encoder.encode("123456789"));
        user = userRepo.save(user);
        Assert.notNull(userDetails.fromEntity(user), "Null");
    }

    @Test
    void loadUserByUsername() {
        User user = new User();
        Role userRole = roleRepo.findByName("ROLE_ADMIN");
        user.setUsername("Mihkel");
        user.setRole(userRole);
        user.setPassword(encoder.encode("123456789"));
        user = userRepo.save(user);
        Assert.notNull(userDetailsService.loadUserByUsername(user.getUsername()), "Null");
    }

    @Test
    void usersDto() {
        Object object = controller.usersDto();
        Assert.isInstanceOf(List.class, object);
    }

    @Test
    void rolesDto() {
        Object object = controller.rolesDto();
        Assert.isInstanceOf(List.class, object);
    }

    @Test
    void doRegister() {
        registrationRequest.setUsername("Anneli");
        registrationRequest.setPassword("pussycat");
        Address address = new Address();
        address.setCityName("Tallinn");
        address.setStreetName("Tondi");
        address.setPostalCode(44444);
        registrationRequest.setAddress(address);
        Assert.notNull(controller.register(registrationRequest), "Null");
    }

    @Test
    void doAuth() {
        registrationRequest.setUsername("BugsBunny");
        registrationRequest.setPassword("r@bb1t");
        Address address = new Address();
        address.setCityName("Miami");
        address.setStreetName("Beach road");
        address.setPostalCode(71613);
        controller.register(registrationRequest);
        authRequest.setUsername(registrationRequest.getUsername());
        authRequest.setPassword(registrationRequest.getPassword());
        Assert.isInstanceOf(AuthResponse.class, controller.authResponse(authRequest));
    }

    @Test
    void getAdmin() {
        Assert.hasText(testController.getAdmin(), "I'm an admin");
    }

    @Test
    void getUser() {
        Assert.hasText(testController.getUser(), "I'm a user");
    }

    @Test
    void isUserDto() {
        User user = new User();
        Role userRole = roleRepo.findByName("ROLE_ADMIN");
        user.setUsername("Tanel");
        user.setRole(userRole);
        user.setPassword(encoder.encode("123456789"));
        user = userRepo.save(user);
        Assert.isInstanceOf(
                UserDTO.class, userService.modelMapper().
                        map(userService.findByUserName(
                                user.getUsername()), UserDTO.class));
    }

    @Test
    void isRoleDto() {
        User user = new User();
        Role userRole = roleRepo.findByName("ROLE_ADMIN");
        user.setUsername("Siim");
        user.setRole(userRole);
        user.setPassword(encoder.encode("123456789"));
        user = userRepo.save(user);
        Assert.isInstanceOf(
                RoleDTO.class, userService.modelMapper().
                        map(userService.findByRoleName(
                                user.getRole().getName()), RoleDTO.class));
    }

    @Test
    void deleteUser() {
        User user = new User();
        Role userRole = roleRepo.findByName("ROLE_USER");
        user.setUsername("Riho");
        user.setRole(userRole);
        user.setPassword(encoder.encode("123456789"));
        user = userRepo.save(user);
        Assert.notNull(user, "Null");
        controller.deleteUser(user.getUsername());
        Assert.isNull(userService.findByUserName(user.getUsername()), "Not null");
    }

    @Test
    void updateUser() {
        User user = new User();
        Role userRole = roleRepo.findByName("ROLE_USER");
        user.setUsername("Mikk");
        user.setRole(userRole);
        user.setPassword(encoder.encode("123456789"));
        user = userRepo.save(user);
        Assert.notNull(user, "Null");
        String newUsername = "Sven";
        controller.updateUser(user.getUsername(), newUsername);
        Assert.isNull(userService.findByUserName(user.getUsername()), "Not null");
        Assert.notNull(userService.findByUserName(newUsername), "Null");
    }
}
