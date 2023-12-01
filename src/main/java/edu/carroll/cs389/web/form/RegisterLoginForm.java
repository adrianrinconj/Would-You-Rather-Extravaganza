package edu.carroll.cs389.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterLoginForm {

    @NotNull
    @Size(min = 4, message = "username must contain at least 4 characters")
    private String username;
    @NotNull
    @Size(min = 8, message = "password must contain at least 8 characters")
    private String rawPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

}