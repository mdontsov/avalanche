package com.avalanchelabs.app.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String username;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
