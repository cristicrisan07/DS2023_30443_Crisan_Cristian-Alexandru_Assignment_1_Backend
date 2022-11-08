package com.example.online_energy_utility_platform_backend.service;

import com.example.online_energy_utility_platform_backend.model.Manager;
import com.example.online_energy_utility_platform_backend.model.ManagerDTO;
import com.example.online_energy_utility_platform_backend.repository.ManagerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ManagerOperationsService {
    private final ManagerRepository managerRepository;
    private final DTOConverter dtoConverter;
    private final String secretKey = "JHKLXABYZC!!!!";

    public ManagerOperationsService(ManagerRepository managerRepository, DTOConverter dtoConverter) {
        this.managerRepository = managerRepository;
        this.dtoConverter = dtoConverter;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerOperationsService.class);

    public String createManager(ManagerDTO managerDTO){

        managerDTO.setPassword(PasswordManager.encrypt(managerDTO.getPassword(), secretKey));
        String validityOfManagerData=AccountsValidator.isManagerAccountValid(managerDTO);
        if(validityOfManagerData.equals("valid")) {
            if(managerRepository.findByUsername(managerDTO.getUsername()).isPresent()){
                LOGGER.info("The username:" +managerDTO.getUsername() +" is already used.");
                return "Username already taken.";

            }
            else{
                if(managerRepository.findByEmail(managerDTO.getEmail()).isPresent()){
                    LOGGER.info("The email:" +managerDTO.getEmail()+" is already used.");
                    return "This email address is already used.";
                }
            }
            managerRepository.save(dtoConverter.fromDTOtoManager(managerDTO));
            LOGGER.info("The account with the username:" +managerDTO.getUsername() +" and email: "+managerDTO.getEmail()+" has been successfully created.");
            return "The account has been successfully created.";
        }
        else{
            return validityOfManagerData;
        }
    }
    public ManagerDTO getManagerWithUserAndPass(String username,String password){
        Optional<Manager> manager=managerRepository.findByUsername(username);
        if(manager.isPresent()){
            if(Objects.equals(PasswordManager.decrypt(manager.get().getPassword(), secretKey), password)){
                return dtoConverter.fromManagerToDTO(manager.get());
            }
            LOGGER.info("Wrong password for: "+ username);
        }
        LOGGER.info("Wrong username: "+ username);
        return null;
    }
}
