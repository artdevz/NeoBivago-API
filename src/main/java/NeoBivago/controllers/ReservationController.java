package NeoBivago.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NeoBivago.dto.reservation.ReservationDTO;
import NeoBivago.entities.Reservation;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.exceptions.UnauthorizedDateException;
import NeoBivago.repositories.ReservationRepository;
import NeoBivago.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RequestMapping("/reservation")
@RestController
public class ReservationController {
    
    @Autowired
    ReservationRepository rr;

    @Autowired
    ReservationService rs;

    // CRUD:

    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody @Valid ReservationDTO data) {

        Reservation newReservation = new Reservation(data.user(), data.hotel(), data.room(), data.checkIn(), data.checkOut(), data.nop(), data.price());

        try {
            this.rs.create(newReservation);
            return new ResponseEntity<>("Created Reservation", HttpStatus.CREATED);
        }
        
        catch (UnauthorizedDateException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        catch (ExistingAttributeException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.CONFLICT);
        }
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readAllReservations() {

        List<Reservation> reservationList = this.rr.findAll();

        return new ResponseEntity<>(reservationList, HttpStatus.OK);

    }

    @Operation(summary = "Find All Reservations in NeoBivago", description = "Return a list of all reservations registered in NeoBivago.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Reservation>> findReservationById(@PathVariable UUID id) {

        Optional<Reservation> reservation = this.rr.findById(id);

        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateReservation(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {

        try {
            this.rs.update(id, fields);
            return new ResponseEntity<>("Updated Reservation", HttpStatus.OK);
        } 
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable UUID id) {

        try {
            this.rs.delete(id);
            return new ResponseEntity<>("Deleted Reservation", HttpStatus.OK);
        } 
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
