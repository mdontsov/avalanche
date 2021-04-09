package com.avalanchelabs.app.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public interface MyUserDetails extends UserDetails {
}
