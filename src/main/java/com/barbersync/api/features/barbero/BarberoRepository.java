package com.barbersync.api.features.barbero;

import com.barbersync.api.features.barbero.entities.Barbero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberoRepository extends JpaRepository<Barbero, Integer> {
}
