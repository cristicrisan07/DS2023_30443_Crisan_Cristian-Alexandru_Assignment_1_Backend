package com.example.online_energy_utility_platform_backend.model;

import java.util.Set;

public class    MeteringDeviceDTO {
    private long Id;
    private String description;
    private String address;
    private double max_hour_energy_cons;
    //private Set<EnergyConsumptionDTO> energyConsumption;
    private String clientUsername;

    public MeteringDeviceDTO(long Id,String description, String address, double max_hour_energy_cons,String clientUsername) {
        this.Id=Id;
        this.description = description;
        this.address = address;
        this.max_hour_energy_cons = max_hour_energy_cons;
        //this.energyConsumption = energyConsumption;
        this.clientUsername=clientUsername;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public MeteringDeviceDTO(String description, String address, double max_hour_energy_cons) {
        this.description = description;
        this.address = address;
        this.max_hour_energy_cons = max_hour_energy_cons;
    }

    public MeteringDeviceDTO() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMax_hour_energy_cons() {
        return max_hour_energy_cons;
    }

    public void setMax_hour_energy_cons(double max_hour_energy_cons) {
        this.max_hour_energy_cons = max_hour_energy_cons;
    }

//    public Set<EnergyConsumptionDTO> getEnergyConsumption() {
//        return energyConsumption;
//    }
//
//    public void setEnergyConsumption(Set<EnergyConsumptionDTO> energyConsumption) {
//        this.energyConsumption = energyConsumption;
//    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }
}
