package com.example.online_energy_utility_platform_backend.model;

public class DeviceConsumptionAlertDTO {


    private final String finalMessage;
    private final String clientUsername;

    public DeviceConsumptionAlertDTO(String finalMessage, String clientUsername) {
        this.finalMessage = finalMessage;
        this.clientUsername = clientUsername;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public String getFinalMessage() {
        return finalMessage;
    }
}
