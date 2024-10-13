package NeoBivago.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import NeoBivago.models.HotelModel;

@Repository
public interface HotelRepository extends JpaRepository<HotelModel, UUID> {

    HotelModel findByName(String hotelName);

}
