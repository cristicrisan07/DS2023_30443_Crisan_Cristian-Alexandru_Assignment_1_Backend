package com.example.online_energy_utility_platform_backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "managers")
public class Manager extends User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    public Manager(String username, String email, String password) {
        super(username, email, password);
    }

    public Long getId() {
        return id;
    }

    public Manager() {

    }
}

