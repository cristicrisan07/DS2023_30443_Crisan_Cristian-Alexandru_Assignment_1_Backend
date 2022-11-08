package com.example.online_energy_utility_platform_backend.service;

import com.example.online_energy_utility_platform_backend.model.*;
import com.example.online_energy_utility_platform_backend.model.MeteringDevice;
import com.example.online_energy_utility_platform_backend.model.MeteringDeviceDTO;
import com.example.online_energy_utility_platform_backend.repository.ClientRepository;
import com.example.online_energy_utility_platform_backend.repository.MeteringDeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MeteringDeviceOperationsService {

    private final MeteringDeviceRepository meteringDeviceRepository;
    private final ClientRepository clientRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MeteringDeviceOperationsService.class);
    private final DTOConverter dtoConverter;

    public MeteringDeviceOperationsService(MeteringDeviceRepository meteringDeviceRepository, DTOConverter dtoConverter, ClientRepository clientRepository) {
        this.meteringDeviceRepository = meteringDeviceRepository;
        this.clientRepository=clientRepository;
        this.dtoConverter=dtoConverter;
    }

    public String createMeteringDevice(MeteringDeviceDTO meteringDeviceDTO) {

        try {
            meteringDeviceRepository.save(dtoConverter.fromDTOtoMeteringDevice(meteringDeviceDTO));
        }
        catch (DataAccessException exception){
            exception.printStackTrace();
            String errorMessage="Could not create metering device: \n Description: "+meteringDeviceDTO.getDescription() + "\n Address: " + meteringDeviceDTO.getAddress();
            LOGGER.error(errorMessage);
            return null;
        }

            return "The metering device has been successfully created.";
    }

    public List<MeteringDeviceDTO> getMeteringDevices(){
        Iterable<MeteringDevice> iterableMeteringDevices = meteringDeviceRepository.findAll();
        ArrayList<MeteringDevice> meteringDevices= new ArrayList<>();
        iterableMeteringDevices.forEach(meteringDevices::add);
        return meteringDevices.stream().map(dtoConverter::fromMeteringDeviceToDTO).toList();
    }

    public String updateMeteringDeviceById(MeteringDeviceDTO meteringDeviceDTO) {

        Optional<MeteringDevice> meteringDevice = meteringDeviceRepository.findById(meteringDeviceDTO.getId());

        if (meteringDevice.isPresent()) {
            MeteringDevice databaseMeteringDevice=meteringDevice.get();
            databaseMeteringDevice.setDescription(meteringDeviceDTO.getDescription());
            databaseMeteringDevice.setAddress(meteringDeviceDTO.getAddress());
            databaseMeteringDevice.setMax_hour_energy_cons(meteringDeviceDTO.getMax_hour_energy_cons());
            Optional<Client> client=clientRepository.findByUsername(meteringDeviceDTO.getClientUsername());
            if(client.isPresent()) {
                databaseMeteringDevice.setClient(client.get());
            }
            else{
                LOGGER.error("The username: "+meteringDeviceDTO.getClientUsername()+" intended to be tied with the metering device with id: "+ meteringDeviceDTO.getId()+" does not exist!");

                return null;
            }
            meteringDeviceRepository.save(databaseMeteringDevice);

            return "The metering device has been successfully updated.";
        }

        return null;
    }
    public String deleteMeteringDeviceWithId(String id) {

        long mdId;

        try{
           mdId=Long.parseLong(id);
        }
        catch(NumberFormatException e){
            e.printStackTrace();

            return "Invalid metering device id: " + id;
        }

        Optional<MeteringDevice> meteringDevice = meteringDeviceRepository.findById(mdId);

        if (meteringDevice.isPresent()) {
            meteringDeviceRepository.delete(meteringDevice.get());

            return "The metering device with id: " + id + " has been successfully deleted.";
        }

        return null;
    }

    public String linkMeteringDeviceToClient(String id,String username) {

        long mdId;

        try{
            mdId=Long.parseLong(id);
        }
        catch(NumberFormatException e){
            e.printStackTrace();

            return "Invalid metering device id: " + id;
        }

        Optional<MeteringDevice> meteringDevice = meteringDeviceRepository.findById(mdId);

        if (meteringDevice.isPresent()) {
            Optional<Client> client=clientRepository.findByUsername(username);

            if(client.isPresent()) {
                meteringDevice.get().setClient(client.get());
                meteringDeviceRepository.save(meteringDevice.get());
                return "The client with username: "+username+" has been successfully linked to metering device with id: " + id + ".";
            }
            else{
                return "The client with username: "+username+" was not found";
            }
        }

        return null;
    }


}
