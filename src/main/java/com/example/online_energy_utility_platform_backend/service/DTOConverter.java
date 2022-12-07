package com.example.online_energy_utility_platform_backend.service;

import com.example.online_energy_utility_platform_backend.model.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DTOConverter {
    public Client fromDTOtoClient(ClientDTO clientDTO){
        return new Client(clientDTO.getUsername(), clientDTO.getEmail(), clientDTO.getPassword(), clientDTO.getFirstName(), clientDTO.getLastName());
    }
    public Manager fromDTOtoManager(ManagerDTO managerDTO) {
        return new Manager(managerDTO.getUsername(),managerDTO.getEmail(),managerDTO.getPassword());
    }
    public ManagerDTO fromManagerToDTO(Manager manager){
        return new ManagerDTO(manager.getUsername(), manager.getEmail(), manager.getPassword());
    }
    public ClientDTO fromClientToDTO(Client client){
        return new ClientDTO(client.getUsername(), client.getEmail() , "", client.getFirstName(), client.getLastName());
    }

    public EnergyConsumptionDTO fromEnergyConsumptionToDTO(EnergyConsumption energyConsumption){
        return new EnergyConsumptionDTO(energyConsumption.getTimestamp(), energyConsumption.getValue_in_kWh());
    }

    public MeteringDeviceDTO fromMeteringDeviceToDTO(MeteringDevice meteringDevice){

        Client client = meteringDevice.getClient();

       // return new MeteringDeviceDTO(meteringDevice.getId(), meteringDevice.getDescription(), meteringDevice.getAddress(), meteringDevice.getMax_hour_energy_cons(), meteringDevice.getEnergyConsumptionSet().stream().map(this::fromEnergyConsumptionToDTO).collect(Collectors.toSet()),client == null ? "": client.getUsername());
        return new MeteringDeviceDTO(meteringDevice.getId(), meteringDevice.getDescription(), meteringDevice.getAddress(), meteringDevice.getMax_hour_energy_cons(),client == null ? "": client.getUsername());

    }

    public MeteringDevice fromDTOtoMeteringDevice(MeteringDeviceDTO meteringDeviceDTO){
        return new MeteringDevice(meteringDeviceDTO.getDescription(), meteringDeviceDTO.getAddress(), meteringDeviceDTO.getMax_hour_energy_cons());
    }


}
