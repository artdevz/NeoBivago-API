package NeoBivago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import NeoBivago.enums.ERoom;

public interface ERoomRepository extends JpaRepository<ERoom, Long> {
    
    ERoom findByCategory(String category);

}
