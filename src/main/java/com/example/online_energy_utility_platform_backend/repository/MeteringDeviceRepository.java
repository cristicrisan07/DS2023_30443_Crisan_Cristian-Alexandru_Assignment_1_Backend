package com.example.online_energy_utility_platform_backend.repository;

import com.example.online_energy_utility_platform_backend.model.MeteringDevice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeteringDeviceRepository extends CrudRepository<MeteringDevice,Long> {

}
