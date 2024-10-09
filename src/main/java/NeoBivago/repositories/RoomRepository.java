package NeoBivago.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import NeoBivago.models.RoomModel;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, UUID> {

    RoomModel findByRoomNumber(int number);

} 
