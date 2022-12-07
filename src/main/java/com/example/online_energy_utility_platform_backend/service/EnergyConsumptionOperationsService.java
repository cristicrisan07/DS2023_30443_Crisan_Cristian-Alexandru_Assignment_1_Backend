package com.example.online_energy_utility_platform_backend.service;

import com.example.online_energy_utility_platform_backend.model.EnergyConsumption;
import com.example.online_energy_utility_platform_backend.model.MeteringDeviceDTO;
import com.example.online_energy_utility_platform_backend.repository.EnergyConsumptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EnergyConsumptionOperationsService {

    private final EnergyConsumptionRepository energyConsumptionRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyConsumptionOperationsService.class);

    public EnergyConsumptionOperationsService(EnergyConsumptionRepository energyConsumptionRepository) {
        this.energyConsumptionRepository = energyConsumptionRepository;
    }

    public String createEnergyConsumptionEntry(EnergyConsumption energyConsumption) {

        try {
            energyConsumptionRepository.save(energyConsumption);
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            String errorMessage="Could not create energy consumption entry of metering device with id:" + energyConsumption.getMeteringDevice().getId() + "\n description: " + energyConsumption.getMeteringDevice().getDescription() + "\n on: " + energyConsumption.getTimestamp() +"\n of value: " + energyConsumption.getValue_in_kWh();
            LOGGER.error(errorMessage);
            return null;
        }

        return "The energy consumption entry has successfully been created.";
    }

}
