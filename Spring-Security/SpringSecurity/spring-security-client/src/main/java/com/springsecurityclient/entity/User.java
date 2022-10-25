package com.springsecurityclient.entity;

import lombok.Data;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Data
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    @Column(length = 60)
    private String password;
    private String role;
    private boolean enabled = false;
}