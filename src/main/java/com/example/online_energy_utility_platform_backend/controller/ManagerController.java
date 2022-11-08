package com.example.online_energy_utility_platform_backend.controller;

import com.example.online_energy_utility_platform_backend.model.ClientDTO;
import com.example.online_energy_utility_platform_backend.model.ManagerDTO;
import com.example.online_energy_utility_platform_backend.model.MeteringDeviceDTO;
import com.example.online_energy_utility_platform_backend.service.ClientOperationsService;
import com.example.online_energy_utility_platform_backend.service.ManagerOperationsService;
import com.example.online_energy_utility_platform_backend.service.MeteringDeviceOperationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManagerController {
    private final ManagerOperationsService managerOperationsService;
    private final ClientOperationsService clientOperationsService;
    private final MeteringDeviceOperationsService meteringDeviceOperationsService;


    public ManagerController(ManagerOperationsService managerOperationsService, ClientOperationsService clientOperationsService, MeteringDeviceOperationsService meteringDeviceOperationsService) {
        this.managerOperationsService = managerOperationsService;
        this.clientOperationsService = clientOperationsService;
        this.meteringDeviceOperationsService = meteringDeviceOperationsService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerController.class);

    @PostMapping("/addManager")
    public ResponseEntity<String> insertManager(@RequestBody ManagerDTO managerDTO) {
        String status = managerOperationsService.createManager(managerDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The account has been successfully created.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;

            LOGGER.info("The username:" + managerDTO.getUsername() + " or the email:" + managerDTO.getEmail() + " is already taken.");
        } else {
            LOGGER.info("The manager account with the username: " + managerDTO.getUsername() + " and email: " + managerDTO.getEmail() + " was successfully created.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @GetMapping("/loginManager/{username}/{password}")
    public ResponseEntity<ManagerDTO> loginManager(@PathVariable String username, @PathVariable String password) {
        ManagerDTO manager = managerOperationsService.getManagerWithUserAndPass(username, password);
        HttpStatus httpStatus = HttpStatus.OK;
        if (manager == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The username password combination of supposed manager: " + username + " is invalid.");
            return ResponseEntity.status(httpStatus).body(new ManagerDTO("", "", ""));
        } else {
            LOGGER.info("The manager with the username: " + username + " has successfully logged in.");
        }
        return ResponseEntity.status(httpStatus).body(manager);
    }

    @PostMapping("/addClient")
    public ResponseEntity<String> insertClient(@RequestBody ClientDTO clientDTO) {
        String status = clientOperationsService.createClient(clientDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The account has been successfully created.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The username:" + clientDTO.getUsername() + " or the email:" + clientDTO.getEmail() + " is already taken.");
        } else {
            LOGGER.info("The account with the username: " + clientDTO.getUsername() + " and email: " + clientDTO.getEmail() + " was successfully created.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @GetMapping("/getClients")
    public ResponseEntity<List<ClientDTO>> getClients() {
        List<ClientDTO> clients=clientOperationsService.getClients();
        HttpStatus httpStatus = HttpStatus.OK;
        if (clients.isEmpty()) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("There are no clients available at the moment.");
        }
        return ResponseEntity.status(httpStatus).body(clients);
    }

    @PutMapping("/updateClient")
    public ResponseEntity<String> updateClient(@RequestBody ClientDTO clientDTO) {
        String status = clientOperationsService.updateClientWithUsername(clientDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The account has been successfully updated.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The client with the username: "+clientDTO.getUsername() +" was not found.");
        } else {
            LOGGER.info("The account with the username: " + clientDTO.getUsername() + " was successfully updated.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @DeleteMapping("/deleteClient/{username}")
    public ResponseEntity<String> deleteClient(@PathVariable String username) {
        String status= clientOperationsService.deleteClientWithUsername(username);
        HttpStatus httpStatus = HttpStatus.OK;
        if (status == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The username of supposed manager: " + username + " is invalid.");

        } else {
            LOGGER.info("The client with the username: " + username + " has successfully been deleted.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @PostMapping("/addMeteringDevice")
    public ResponseEntity<String> insertMeteringDevice(@RequestBody MeteringDeviceDTO meteringDeviceDTO) {
        String status = meteringDeviceOperationsService.createMeteringDevice(meteringDeviceDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The metering device has been successfully created.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
        } else {
            LOGGER.info("Metering device: \n Description: "+meteringDeviceDTO.getDescription() + "\n Address: " + meteringDeviceDTO.getAddress() + "\n successfully created.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @GetMapping("/getMeteringDevices")
    public ResponseEntity<List<MeteringDeviceDTO>> getMeteringDevices() {
        List<MeteringDeviceDTO> devices= meteringDeviceOperationsService.getMeteringDevices();
        HttpStatus httpStatus = HttpStatus.OK;
        if (devices.isEmpty()) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("There are no metering devices available at the moment.");
        }
        return ResponseEntity.status(httpStatus).body(devices);
    }

    @PutMapping("/updateMeteringDevice")
    public ResponseEntity<String> updateMeteringDevice(@RequestBody MeteringDeviceDTO meteringDeviceDTO) {
        String status = meteringDeviceOperationsService.updateMeteringDeviceById(meteringDeviceDTO);
        HttpStatus httpStatus = HttpStatus.OK;
        if (!status.equals("The metering device has been successfully updated.")) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The metering device with the id: "+meteringDeviceDTO.getId() +" could not be updated.");
        } else {
            LOGGER.info("The metering device with the Id: " + meteringDeviceDTO.getId() + " was successfully updated.");
        }
        return ResponseEntity.status(httpStatus).body(status);
    }

    @DeleteMapping("/deleteMeteringDevice/{id}")
    public ResponseEntity<String> deleteMeteringDevice(@PathVariable String id) {

        String status = meteringDeviceOperationsService.deleteMeteringDeviceWithId(id);
        HttpStatus httpStatus = HttpStatus.OK;

        if (status == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The id of supposed metering device: " + id  + " is invalid.");
        } else {
            LOGGER.info(status);
        }

        return ResponseEntity.status(httpStatus).body(status);
    }
    @GetMapping("/linkMeteringDeviceToClient/{id}/{username}")
    public ResponseEntity<String> linkMeteringDeviceToClient(@PathVariable String id,@PathVariable String username){
        String status = meteringDeviceOperationsService.linkMeteringDeviceToClient(id,username);

        HttpStatus httpStatus = HttpStatus.OK;

        if (status == null) {
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
            LOGGER.info("The id of supposed metering device: " + id  + " is invalid.");
        } else {
            LOGGER.info(status);
        }

        return ResponseEntity.status(httpStatus).body(status);
    }






}
