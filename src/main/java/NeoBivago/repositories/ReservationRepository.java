package NeoBivago.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import NeoBivago.entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    @Query(nativeQuery = true, value = "SELECT check_in FROM reservations WHERE room_id = :id")
    List<Date> findAllCheckInByRoom(UUID id);

    @Query(nativeQuery = true, value = "SELECT check_out FROM reservations WHERE room_id = :id")
    List<Date> findAllCheckOutByRoom(UUID id);

}
