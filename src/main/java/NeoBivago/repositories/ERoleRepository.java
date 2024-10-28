package NeoBivago.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import NeoBivago.enums.ERole;

public interface ERoleRepository extends JpaRepository<ERole, UUID> {
    
    ERole findByName(String roleName);

}
