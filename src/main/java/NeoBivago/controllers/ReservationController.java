package NeoBivago.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import NeoBivago.dto.reservation.ReservationRequestDTO;
import NeoBivago.dto.reservation.ReservationResponseDTO;
import NeoBivago.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RequestMapping("/reservation")
@RestController
public class ReservationController {
    
    private final ReservationService reservationS;

    public ReservationController(ReservationService reservationS) {
        this.reservationS = reservationS;
    }

    @Operation(
        summary = "Create a new reservation",
        description = "Create a new reservation in the NeoBivago system.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Reservation created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid reservation data."),
            @ApiResponse(responseCode = "409", description = "Conflict with existing reservation or invalid data.")
        }
    )
    @PostMapping    
    public ResponseEntity<String> createReservation(@RequestBody @Valid ReservationRequestDTO data) throws Exception {
        
        reservationS.create(data);
        return new ResponseEntity<>("Created Reservation", HttpStatus.CREATED);

    }

    @Operation(
        summary = "Get all reservations",
        description = "Retrieve a list of all reservations registered in the NeoBivago system.",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of all reservations retrieved successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
        }
    )
    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> readAllReservations() {

        return new ResponseEntity<>(reservationS.readAll(), HttpStatus.OK);

    }

    @Operation(
        summary = "Get a reservation by ID",
        description = "Retrieve details of a specific reservation by its ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Reservation found."),
            @ApiResponse(responseCode = "404", description = "Reservation not found.")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> findReservationById(@PathVariable UUID id) {

        return new ResponseEntity<>(reservationS.readById(id), HttpStatus.OK);

    }

    @Operation(
        summary = "Update reservation details",
        description = "Update the details of an existing reservation by providing its ID and the fields to update.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Reservation updated successfully."),
            @ApiResponse(responseCode = "404", description = "Reservation not found."),
            @ApiResponse(responseCode = "400", description = "Invalid fields or data.")
        }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateReservation(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {

        reservationS.update(id, fields);
        return new ResponseEntity<>("Updated Reservation", HttpStatus.OK);

    }

    @Operation(
        summary = "Delete a reservation",
        description = "Delete an existing reservation by providing its ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Reservation deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Reservation not found.")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable UUID id) {
        
        reservationS.delete(id);
        return new ResponseEntity<>("Deleted Reservation", HttpStatus.OK);

    }

}
