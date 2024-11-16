package NeoBivago.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import NeoBivago.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    Room findByNumber(int number);

    @Query(nativeQuery = true, value = "SELECT * FROM rooms WHERE " +
                                       "(:capacity IS NULL OR capacity >= :capacity) AND " +
                                       "(:price IS NULL OR price <= :price) AND " +
                                       "(:category IS NULL OR category_id = :category)" 
    )
    List<Room> roomFilter(Integer capacity, Integer price, Integer category);

} 
