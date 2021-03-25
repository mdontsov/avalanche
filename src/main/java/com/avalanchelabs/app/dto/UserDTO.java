package com.avalanchelabs.app.dto;

import com.avalanchelabs.app.entity.Role;
import com.avalanchelabs.app.json.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long id;
    private String username;
    private String password;
    private Address address;
    private Role role;

}
