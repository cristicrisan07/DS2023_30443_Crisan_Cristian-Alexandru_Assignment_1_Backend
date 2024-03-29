package com.example.online_energy_utility_platform_backend.model;

import java.sql.Timestamp;

public class EnergyConsumptionDTO {
    private String timestamp;
    private double value_in_kWh;

    public EnergyConsumptionDTO(String timestamp, double value_in_kWh) {
        this.timestamp = timestamp;
        this.value_in_kWh = value_in_kWh;
    }

    public EnergyConsumptionDTO(){

    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue_in_kWh() {
        return value_in_kWh;
    }

    public void setValue_in_kWh(double value_in_kWh) {
        this.value_in_kWh = value_in_kWh;
    }
}
