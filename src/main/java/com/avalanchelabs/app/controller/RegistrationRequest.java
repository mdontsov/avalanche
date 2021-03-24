package com.avalanchelabs.app.controller;

import com.avalanchelabs.app.json.Address;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private Address address;
}
