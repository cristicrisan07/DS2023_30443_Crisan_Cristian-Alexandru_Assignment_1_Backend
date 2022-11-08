package com.example.online_energy_utility_platform_backend.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")

public class Client extends User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String firstName;

    @OneToMany(mappedBy = "client")
    private Set<MeteringDevice> meteringDevices;

    public Set<MeteringDevice> getMeteringDevices() {
        return meteringDevices;
    }

    public void setMeteringDevices(Set<MeteringDevice> meteringDevices) {
        this.meteringDevices = meteringDevices;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Column(nullable = false)
    private String lastName;

    public Client(String username, String email, String password, String firstName, String lastName) {
        super(username, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Client() {

    }
}

