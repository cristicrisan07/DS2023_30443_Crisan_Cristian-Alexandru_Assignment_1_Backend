package com.example.online_energy_utility_platform_backend.repository;

import com.example.online_energy_utility_platform_backend.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client,Long> {
    Optional<Client> findByUsername(String username);
    Optional<Client> findByEmail(String email);

    Iterable<Client> findAll();

}
