package com.example.online_energy_utility_platform_backend.model;

public class ManagerDTO {
    private String username;
    private String email;
    private String password;

    public ManagerDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public ManagerDTO(){

    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
