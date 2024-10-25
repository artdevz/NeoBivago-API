package NeoBivago.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import NeoBivago.entities.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    Hotel findByName(String hotelName);

}
