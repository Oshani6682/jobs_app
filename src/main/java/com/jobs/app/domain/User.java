package com.jobs.app.domain;

import com.jobs.app.dto.UserDTO;
import com.jobs.app.enums.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "user_name", unique = true, nullable = false)
    public String userName;

    @Column(name = "first_name", nullable = false)
    public String firstName;

    @Column(name = "last_name", nullable = false)
    public String lastName;

    @Column(nullable = false)
    public String address;

    @Column(nullable = false)
    public String password;

    @Column(nullable = false)
    public String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    public UserRole userRole;

    @Column(name = "is_active")
    public Boolean isActive = true;

    public User() {}

    public User(UserDTO userDTO) {
        firstName = userDTO.getFirstName().trim();
        lastName = userDTO.getLastName().trim();
        address = userDTO.getAddress().trim();
        email = userDTO.getEmail().trim();
    }

}
