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

import NeoBivago.dto.hotel.HotelDTO;
import NeoBivago.entities.Hotel;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.exceptions.LenghtException;
import NeoBivago.repositories.HotelRepository;
import NeoBivago.services.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RequestMapping("/hotel")
@RestController
public class HotelController {
    
    @Autowired
    HotelRepository hr;

    @Autowired
    HotelService hs;

    // CRUD:

    @PostMapping
    public ResponseEntity<String> createHotel(@RequestBody @Valid HotelDTO data) {

        Hotel newHotel = new Hotel(data.owner(), data.name(), data.address(), data.city(), data.score());

        try {
            this.hs.create(newHotel);
            return new ResponseEntity<>("Created Hotel", HttpStatus.CREATED);
        }
        
        catch (ExistingAttributeException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.CONFLICT);
        }

        catch (LenghtException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.FORBIDDEN);
        }
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(summary = "Find All Hotels in NeoBivago", description = "Return a list of all hotels registered in NeoBivago.")
    @GetMapping
    public ResponseEntity<List<Hotel>> readAllHotels() {

        List<Hotel> hotelList = this.hr.findAll();

        return new ResponseEntity<>(hotelList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Hotel>> findHotelById(@PathVariable UUID id) {

        Optional<Hotel> hotel = this.hr.findById(id);

        return new ResponseEntity<>(hotel, HttpStatus.OK);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateHotel(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {

        try {
            this.hs.update(id, fields);
            return new ResponseEntity<>("Updated Hotel", HttpStatus.OK);
        } 
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable UUID id) {

        try {
            this.hs.delete(id);
            return new ResponseEntity<>("Deleted Hotel", HttpStatus.OK);
        } 
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
