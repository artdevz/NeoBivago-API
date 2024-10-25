package NeoBivago.enums;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ERoleRepository extends JpaRepository<ERole, UUID> {
    
    Optional<ERole> findById(ERole role);

}
