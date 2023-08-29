package com.jobs.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDTO {

    @NotBlank(message = "UserCredentialsInvalid#User name is not defined")
    @Pattern(
        regexp = "^[A-Za-z0-9]+$",
        message = "UserCredentialsInvalid#User name must only contain letters & numbers"
    )
    @Size(
        min = 6,
        message = "UserCredentialsInvalid#User name is too short"
    )
    @Size(
        max = 45,
        message = "UserCredentialsInvalid#User name is too long"
    )
    String userName;


    @NotBlank(message = "UserCredentialsInvalid#User password is not defined")
    @Size(
        min = 6,
        message = "UserCredentialsInvalid#User password is too short"
    )
    @Size(
        max = 20,
        message = "UserCredentialsInvalid#User password is too long"
    )
    String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
