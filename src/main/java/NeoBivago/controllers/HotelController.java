package NeoBivago.controllers;

import java.util.List;
import java.util.Optional;
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

import NeoBivago.dto.hotel.HotelDTO;
import NeoBivago.models.HotelModel;
import NeoBivago.repositories.HotelRepository;
import NeoBivago.services.HotelService;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RequestMapping("/api/hotel")
@RestController
public class HotelController {
    
    @Autowired
    HotelRepository hr;

    @Autowired
    HotelService hs;

    // CRUD:

    @PostMapping
    public ResponseEntity<String> createHotel(@RequestBody @Valid HotelDTO data) {

        HotelModel newHotel = new HotelModel(data.owner(), data.name(), data.address(), data.city(), data.score());

        try {
            this.hs.create(newHotel);
            return new ResponseEntity<>("Created new Hotel", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity<List<HotelModel>> readAllHotels() {

        List<HotelModel> hotelList = this.hr.findAll();

        return new ResponseEntity<>(hotelList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<HotelModel>> findHotelById(@PathVariable UUID id) {

        Optional<HotelModel> hotel = this.hr.findById(id);

        return new ResponseEntity<>(hotel, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateHotel(@RequestBody HotelModel hotel, @PathVariable UUID id) {

        try {
            this.hs.update(id, hotel);
            return new ResponseEntity<>("Updated Hotel", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable UUID id) {

        try {
            this.hs.delete(id);
            return new ResponseEntity<>("Deleted Hotel", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
