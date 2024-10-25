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

import NeoBivago.dto.room.RoomDTO;
import NeoBivago.dto.room.RoomFilterDTO;
import NeoBivago.entities.Room;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.repositories.RoomRepository;
import NeoBivago.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RequestMapping("/room")
@RestController
public class RoomController {
    
    @Autowired
    RoomRepository rr;

    @Autowired
    RoomService rs;

    // CRUD:

    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody @Valid RoomDTO data) {

        Room newRoom = new Room(data.hotel(), data.number(), data.capacity(), data.price(), data.type());

        try {
            this.rs.create(newRoom);
            return new ResponseEntity<>("Created Room", HttpStatus.CREATED);
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

        List<Room> roomList = this.rr.findAll();

        return new ResponseEntity<>(roomList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Room>> findRoomById(@PathVariable UUID id) {

        Optional<Room> room = this.rr.findById(id);

        return new ResponseEntity<>(room, HttpStatus.OK);

    }
    
    @Operation(summary = "Find All Rooms in NeoBivago", description = "Return a list of all rooms registered in NeoBivago.")
    @GetMapping("/filter")
    public ResponseEntity<List<Room>> readAllRoomsWithFilter(@RequestBody @Valid RoomFilterDTO data) {

        List<Room> roomList = this.rr.roomFilter(data.capacity(), data.price(), data.type());

        return new ResponseEntity<>(roomList, HttpStatus.OK);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateRoom(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {

        try {
            this.rs.update(id, fields);
            return new ResponseEntity<>("Updated Room", HttpStatus.OK);
        } 
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable UUID id) {

        try {
            this.rs.delete(id);
            return new ResponseEntity<>("Deleted Room", HttpStatus.OK);
        } 
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
