package com.avalanchelabs.app.controller;

import com.avalanchelabs.app.json.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RegistrationRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private Address address;
}
