package NeoBivago.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NeoBivago.models.ReservationModel;
import NeoBivago.repositories.ReservationRepository;

@Service
public class ReservationService {
    
    @Autowired
    ReservationRepository rr;

    public void create(ReservationModel reservation) throws Exception {

        this.rr.save(reservation);

    }

    public void update(UUID id, ReservationModel reservation) {
        
        reservation.setReservationId(id);
        this.rr.save(reservation);

    }

    public void delete(UUID id) {
        this.rr.deleteById(id);
    }

}
