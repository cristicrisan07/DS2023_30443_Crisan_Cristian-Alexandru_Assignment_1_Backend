package com.example.online_energy_utility_platform_backend.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "metering_devices")
public class MeteringDevice {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String description;
    @Column
    private String address;
    @Column
    private double max_hour_energy_cons;

    @OneToMany(mappedBy = "meteringDevice")
    private Set<EnergyConsumption> energyConsumptionSet;

    public Set<EnergyConsumption> getEnergyConsumptionSet() {
        return energyConsumptionSet;
    }

    public void setEnergyConsumptionSet(Set<EnergyConsumption> energyConsumptionSet) {
        this.energyConsumptionSet = energyConsumptionSet;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne
    private Client client;

    public MeteringDevice(String description, String address, double max_hour_energy_cons) {
        this.description = description;
        this.address = address;
        this.max_hour_energy_cons = max_hour_energy_cons;
    }

    public MeteringDevice() {

    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public double getMax_hour_energy_cons() {
        return max_hour_energy_cons;
    }

    public Long getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMax_hour_energy_cons(double max_hour_energy_cons) {
        this.max_hour_energy_cons = max_hour_energy_cons;
    }
}
