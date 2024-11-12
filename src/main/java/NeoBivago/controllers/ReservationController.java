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
import org.springframework.web.server.ResponseStatusException;

import NeoBivago.dto.ReservationDTO;
import NeoBivago.exceptions.CapacityExceededException;
import NeoBivago.models.Reservation;
import NeoBivago.repositories.ReservationRepository;
import NeoBivago.services.MappingService;
import NeoBivago.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RequestMapping("/reservation")
@RestController
public class ReservationController {
    
    @Autowired
    ReservationRepository reservationR;

    @Autowired
    ReservationService reservationS;

    @Autowired
    MappingService mappingS;

    @PostMapping    
    public ResponseEntity<String> createReservation(@RequestBody @Valid ReservationDTO data) throws Exception {
        
        this.reservationS.create(new Reservation(mappingS.findUserById(data.user()), mappingS.findRoomById(data.room()), data.checkIn(), data.checkOut(), data.nop(), data.price()));
        return new ResponseEntity<>("Created Reservation", HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readAllReservations() {

        List<Reservation> reservationList = this.reservationR.findAll();

        return new ResponseEntity<>(reservationList, HttpStatus.OK);

    }

    @Operation(summary = "Find All Reservations in NeoBivago", description = "Return a list of all reservations registered in NeoBivago.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Reservation>> findReservationById(@PathVariable UUID id) {

        Optional<Reservation> reservation = this.reservationR.findById(id);

        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateReservation(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {

        try {
            this.reservationS.update(id, fields);
            return new ResponseEntity<>("Updated Reservation", HttpStatus.OK);
        } 
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable UUID id) {

        try {
            this.reservationS.delete(id);
            return new ResponseEntity<>("Deleted Reservation", HttpStatus.OK);
        }
        
        catch (ResponseStatusException e) {
            return new ResponseEntity<>("Reservation not found.", HttpStatus.NOT_FOUND);
        }

        catch (CapacityExceededException e) {
            return new ResponseEntity<>(e.getMessage() + " Try to book fewer people.", HttpStatus.BAD_REQUEST);
        }
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
