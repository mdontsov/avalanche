package com.avalanchelabs.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * User password encryption
 */
@Configuration
public class SecurityConfiguration {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
