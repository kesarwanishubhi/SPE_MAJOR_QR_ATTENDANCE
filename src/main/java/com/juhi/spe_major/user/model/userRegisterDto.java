package com.juhi.spe_major.user.model;  // Adjust the package name according to your project structure

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;



public class userRegisterDto {

    @NotBlank(message = "Username cannot be null")

    private String username;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Role cannot be null")
    private String role;

    private String imagePath;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
