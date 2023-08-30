package com.jobs.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jobs.app.domain.User;
import com.jobs.app.enums.UserRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    Integer id;

    @NotBlank(message = "UserInfoInvalid#User first name is not defined")
    String firstName;

    @NotBlank(message = "UserInfoInvalid#User last name is not defined")
    String lastName;

    @NotBlank(message = "UserInfoInvalid#User address is not defined")
    String address;

    @NotBlank(message = "UserInfoInvalid#User email is not defined")
    @Pattern(
        regexp = "^[a-zA-Z0-9]+[a-zA-Z0-9._]+@[a-zA-Z.]+\\.[a-zA-Z]+$",
        message = "UserInfoInvalid#Uer email is invalid"
    )
    String email;

    @JsonIgnore
    UserRole userRole;

    Boolean isActive;

    public UserDTO() {}

    public UserDTO(User user) {
        setId(user.id);
        setFirstName(user.firstName);
        setLastName(user.lastName);
        setAddress(user.address);
        setEmail(user.email);
        setActive(user.isActive);
    }

    public UserDTO(User user, boolean includeUserRole) {
        this(user);
        if (includeUserRole) {
            this.userRole = user.userRole;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public UserRole getUserRole() {
        return userRole;
    }

}
