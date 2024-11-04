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

import NeoBivago.dto.room.RoomDTO;
import NeoBivago.dto.room.RoomFilterDTO;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.models.Room;
import NeoBivago.repositories.RoomRepository;
import NeoBivago.services.MappingService;
import NeoBivago.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RequestMapping("/room")
@RestController
public class RoomController {
    
    @Autowired
    RoomRepository roomR;

    @Autowired
    RoomService roomS;

    @Autowired
    MappingService mappingS;

    // CRUD:

    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody @Valid RoomDTO data) {
        
        
        try {
            Room newRoom = new Room(mappingS.findHotelById(data.hotel()), data.number(), data.capacity(), data.price(), mappingS.getCategory(data.category().getCategory()));
            this.roomS.create(newRoom);
            return new ResponseEntity<>("Created Room", HttpStatus.CREATED);
        }

        catch (ResponseStatusException e) {
            return new ResponseEntity<>("Hotel not found.", HttpStatus.NOT_FOUND);
        }

        catch (ExistingAttributeException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.CONFLICT);
        }
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity<List<Room>> readAllRooms() {

        List<Room> roomList = this.roomR.findAll();

        return new ResponseEntity<>(roomList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Room>> findRoomById(@PathVariable UUID id) {

        Optional<Room> room = this.roomR.findById(id);

        return new ResponseEntity<>(room, HttpStatus.OK);

    }
    
    @Operation(summary = "Find All Rooms in NeoBivago", description = "Return a list of all rooms registered in NeoBivago.")
    @GetMapping("/filter")
    public ResponseEntity<List<Room>> readAllRoomsWithFilter(@RequestBody @Valid RoomFilterDTO data) {

        return new ResponseEntity<>(this.roomS.filter(
            this.roomR.roomFilter(
                data.capacity(), data.price(), mappingS.getId(data.category().getCategory()).intValue()), data.city()),
            HttpStatus.OK);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateRoom(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {

        try {
            this.roomS.update(id, fields);
            return new ResponseEntity<>("Updated Room", HttpStatus.OK);
        } 
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable UUID id) {

        try {
            this.roomS.delete(id);
            return new ResponseEntity<>("Deleted Room", HttpStatus.OK);
        }
        
        catch (ResponseStatusException e) {
            return new ResponseEntity<>("Room not found.", HttpStatus.NOT_FOUND);
        }
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
