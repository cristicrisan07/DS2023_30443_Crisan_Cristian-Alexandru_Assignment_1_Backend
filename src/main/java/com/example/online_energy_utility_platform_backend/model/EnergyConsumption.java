package com.example.online_energy_utility_platform_backend.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "energy_consumption")
public class EnergyConsumption {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String timestamp;

    @Column(nullable = false)
    private double value_in_kWh;

    @ManyToOne
    private MeteringDevice meteringDevice;

    public EnergyConsumption() {

    }

    public MeteringDevice getMeteringDevice() {
        return meteringDevice;
    }

    public void setMeteringDevice(MeteringDevice meteringDevice) {
        this.meteringDevice = meteringDevice;
    }

    public EnergyConsumption(String timestamp, double value) {
        this.timestamp = timestamp;
        this.value_in_kWh = value;
    }

    public Long getId() {
        return id;
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
