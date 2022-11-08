package com.example.online_energy_utility_platform_backend.repository;

import com.example.online_energy_utility_platform_backend.model.Manager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends CrudRepository<Manager,Long> {

    Optional<Manager> findByUsername(String username);
    Optional<Manager> findByEmail(String email);

}
