package NeoBivago.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import NeoBivago.models.ReservationModel;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel, UUID> {}
