package com.avalanchelabs.app.dto;

import com.avalanchelabs.app.entity.Role;
import com.avalanchelabs.app.json.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    public long id;
    public String username;
    public String password;
    public Address address;
    public Role role;

}
