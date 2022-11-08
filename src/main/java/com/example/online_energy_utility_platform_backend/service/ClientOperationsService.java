package com.example.online_energy_utility_platform_backend.service;

import com.example.online_energy_utility_platform_backend.model.Client;
import com.example.online_energy_utility_platform_backend.model.ClientDTO;
import com.example.online_energy_utility_platform_backend.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientOperationsService {

    private final ClientRepository clientRepository;
    private final DTOConverter dtoConverter;
    private final String secretKey = "JHKLXABYZC!!!!";

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOperationsService.class);

    public ClientOperationsService(ClientRepository clientRepository, DTOConverter dtoConverter) {
        this.clientRepository = clientRepository;
        this.dtoConverter = dtoConverter;
    }

    public String createClient(ClientDTO clientDTO) {

        clientDTO.setPassword(PasswordManager.encrypt(clientDTO.getPassword(), secretKey));
        String validityOfClientData = AccountsValidator.isClientAccountValid(clientDTO);
        if (validityOfClientData.equals("valid")) {

            if (clientRepository.findByUsername(clientDTO.getUsername()).isPresent()) {
                LOGGER.info("The username: " + clientDTO.getUsername() + " is already taken.");
                return "Username already taken.";
            } else {
                if (clientRepository.findByEmail(clientDTO.getEmail()).isPresent()) {
                    LOGGER.info("The email address: " + clientDTO.getEmail() + " is already taken.");
                    return "This email address is already used.";
                }
            }
            clientRepository.save(dtoConverter.fromDTOtoClient(clientDTO));

            return "The account has been successfully created.";
        } else {
            return validityOfClientData;
        }
    }
    
    public ClientDTO getClientWithUserAndPass(String username, String password) {
        Optional<Client> client = clientRepository.findByUsername(username);
        if (client.isPresent()) {

            if (Objects.equals(PasswordManager.decrypt(client.get().getPassword(), secretKey), password)) {
                return dtoConverter.fromClientToDTO(client.get());
            }
        }
        return null;
    }

    public List<ClientDTO> getClients(){
        Iterable<Client> iterableClients = clientRepository.findAll();
        ArrayList<Client> clients= new ArrayList<>();
        iterableClients.forEach(clients::add);
        return clients.stream().map(dtoConverter::fromClientToDTO).toList();
    }
    public String updateClientWithUsername(ClientDTO clientDTO) {
        Optional<Client> client = clientRepository.findByUsername(clientDTO.getUsername());
        if (client.isPresent()) {
            Client databaseClient=client.get();
            databaseClient.setFirstName(clientDTO.getFirstName());
            databaseClient.setLastName(clientDTO.getLastName());
            databaseClient.setEmail(clientDTO.getEmail());
            if(!Objects.equals(clientDTO.getPassword(), "")) {
                databaseClient.setPassword(PasswordManager.encrypt(clientDTO.getPassword(), secretKey));
            }
            clientRepository.save(databaseClient);
            return "The account has been successfully updated.";
        }
        return null;
    }

    public String deleteClientWithUsername(String username) {
        Optional<Client> client = clientRepository.findByUsername(username);
        if (client.isPresent()) {
            client.get().getMeteringDevices().forEach(el->el.setClient(null));
            clientRepository.delete(client.get());
            return "The account has been successfully deleted.";
        }
        return null;
    }
    
}
