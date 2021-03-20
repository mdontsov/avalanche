package com.avalanchelabs.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller used for initial API testing purposes
 */

@RestController
public class TestController {

    @GetMapping("/admin/get")
    public String getAdmin() {
        return "I'm an admin";
    }

    @GetMapping("/user/get")
    public String getUser() {
        return "I'm a user";
    }
}
