package NeoBivago.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import NeoBivago.entities.Room;
import NeoBivago.enums.ERoom;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    Room findByNumber(int number);

    @Query(nativeQuery = true, value = "SELECT * FROM rooms WHERE " +
                                       "(:capacity IS NULL OR capacity >= :capacity) AND " +
                                       "(:price IS NULL OR price <= :price) AND " +
                                       "(:type IS NULL OR type = :type)" 
    )
    List<Room> roomFilter(int capacity, int price, ERoom type);

} 
