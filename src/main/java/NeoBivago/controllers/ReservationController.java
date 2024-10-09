package NeoBivago.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NeoBivago.dto.reservation.ReservationDTO;
import NeoBivago.models.ReservationModel;
import NeoBivago.repositories.ReservationRepository;
import NeoBivago.services.ReservationService;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RequestMapping("/api/reservation")
@RestController
public class ReservationController {
    
    @Autowired
    ReservationRepository rr;

    @Autowired
    ReservationService rs;

    // CRUD:

    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@RequestBody @Valid ReservationDTO data) {

        ReservationModel newReservation = new ReservationModel(data.user(), data.hotel(), data.room(), data.checkIn(), data.checkOut(), data.nop(), data.price());

        try {
            this.rs.create(newReservation);
            return new ResponseEntity<>("Created new Reservation", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/read")
    public ResponseEntity<List<ReservationModel>> readAllReservations() {

        List<ReservationModel> reservationList = this.rr.findAll();

        return new ResponseEntity<>(reservationList, HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateReservation(@RequestBody ReservationModel reservation, @PathVariable UUID id) {

        try {
            this.rs.update(id, reservation);
            return new ResponseEntity<>("Updated Reservation", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable UUID id) {

        try {
            this.rs.delete(id);
            return new ResponseEntity<>("Deleted Reservation", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
