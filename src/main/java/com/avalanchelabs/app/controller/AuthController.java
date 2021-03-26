package com.avalanchelabs.app.controller;

import com.avalanchelabs.app.configuration.jwt.JwtProvider;
import com.avalanchelabs.app.dto.RoleDTO;
import com.avalanchelabs.app.dto.UserDTO;
import com.avalanchelabs.app.entity.User;
import com.avalanchelabs.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthController(@Lazy UserService userService, @Lazy JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public String register(@RequestBody @Valid RegistrationRequest request) {
        User user = new User();
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setAddress(request.getAddress());
        userService.saveUser(user);
        return "Registration OK";
    }

    @PostMapping("/auth")
    public AuthResponse authResponse(@RequestBody AuthRequest request) {
        User user = userService.findByUserNameAndPassword(request.getUsername(), request.getPassword());
        String token = jwtProvider.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    @DeleteMapping("/delete/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
    }

    @PutMapping("update/{oldUsername}/{newUsername}")
    public void updateUser(@PathVariable("oldUsername") String oldUsername,
                           @PathVariable("newUsername") String newUsername) {
        userService.updateUser(oldUsername, newUsername);
    }

    @GetMapping("/usersDto")
    public List<UserDTO> usersDto() {
        return Arrays.asList(userService.modelMapper().map(userService.findUsers(), UserDTO[].class));
    }

    @GetMapping("/rolesDto")
    public List<RoleDTO> rolesDto() {
        return Arrays.asList(userService.modelMapper().map(userService.findRoles(), RoleDTO[].class));
    }
}
