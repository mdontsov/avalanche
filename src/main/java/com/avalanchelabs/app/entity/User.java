package com.avalanchelabs.app.entity;

import com.avalanchelabs.app.json.Address;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String username;

    @Column
    private String password;

    @Type(type = "json")
    @Column
    private Address address;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
