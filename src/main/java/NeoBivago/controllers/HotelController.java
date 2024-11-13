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

import NeoBivago.dto.hotel.HotelRequestDTO;
import NeoBivago.models.Hotel;
import NeoBivago.repositories.HotelRepository;
import NeoBivago.services.HotelService;
import NeoBivago.services.MappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RequestMapping("/hotel")
@RestController
public class HotelController {
    
    @Autowired
    HotelRepository hotelR;

    private final HotelService hotelS;

    @Autowired
    MappingService mappingS;    

    public HotelController(HotelService hotelS) {
        this.hotelS = hotelS;
    }

    @Operation(
        summary = "Create a new hotel",
        description = "Create a new hotel in the NeoBivago system.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Hotel created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid hotel data."),
            @ApiResponse(responseCode = "409", description = "Hotel with the same name already exists.")
        }
    )
    @PostMapping
    public ResponseEntity<String> createHotel(@RequestBody @Valid HotelRequestDTO data) throws Exception {
       
        this.hotelS.create(data);
        return new ResponseEntity<>("Created Hotel", HttpStatus.CREATED);
        
    }

    @Operation(
        summary = "Get all hotels",
        description = "Retrieve a list of all hotels registered in the NeoBivago system.",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of all hotels retrieved successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
        }
    )    
    @GetMapping
    public ResponseEntity<List<Hotel>> readAllHotels() {

        List<Hotel> hotelList = this.hotelR.findAll();

        return new ResponseEntity<>(hotelList, HttpStatus.OK);

    }

    @Operation(
        summary = "Get a hotel by ID",
        description = "Retrieve details of a specific hotel by its ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Hotel found."),
            @ApiResponse(responseCode = "404", description = "Hotel not found.")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Hotel>> findHotelById(@PathVariable UUID id) {

        Optional<Hotel> hotel = this.hotelR.findById(id);

        return new ResponseEntity<>(hotel, HttpStatus.OK);

    }

    @Operation(
        summary = "Update a hotel",
        description = "Update information of an existing hotel by providing its ID and the fields to update.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Hotel updated successfully."),
            @ApiResponse(responseCode = "404", description = "Hotel not found."),
            @ApiResponse(responseCode = "400", description = "Invalid fields or data.")
        }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateHotel(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {

        this.hotelS.update(id, fields);
        return new ResponseEntity<>("Updated Hotel", HttpStatus.OK);

    }

    @Operation(
        summary = "Delete a hotel",
        description = "Delete a hotel from the NeoBivago system by providing its ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Hotel deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Hotel not found.")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable UUID id) {
        
        this.hotelS.delete(id);
        return new ResponseEntity<>("Deleted Hotel", HttpStatus.OK);

    }

}
