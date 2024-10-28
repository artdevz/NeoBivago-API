package NeoBivago.enums;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ERoleRepository extends JpaRepository<ERole, UUID> {
    
    ERole findByName(String roleName);

}
