package com.avalanchelabs.app.configuration;

import com.avalanchelabs.app.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Collections;

@Component
@Data
public class UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetails fromEntity(User user) {
        UserDetails userDetails = new UserDetails();
        userDetails.username = user.getUsername();
        userDetails.password = user.getPassword();
        userDetails.authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
        return userDetails;
    }
}
