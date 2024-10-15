package NeoBivago.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NeoBivago.dto.room.RoomDTO;
import NeoBivago.dto.room.RoomFilterDTO;
import NeoBivago.models.RoomModel;
import NeoBivago.repositories.RoomRepository;
import NeoBivago.services.RoomService;
import jakarta.validation.Valid;

@RequestMapping("/api/room")
@RestController
public class RoomController {
    
    @Autowired
    RoomRepository rr;

    @Autowired
    RoomService rs;

    // CRUD:

    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody @Valid RoomDTO data) {

        RoomModel newRoom = new RoomModel(data.hotel(), data.number(), data.capacity(), data.price(), data.type());

        try {
            this.rs.create(newRoom);
            return new ResponseEntity<>("Created Room", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity<List<RoomModel>> readAllRooms() {

        List<RoomModel> roomList = this.rr.findAll();

        return new ResponseEntity<>(roomList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<RoomModel>> findRoomById(@PathVariable UUID id) {

        Optional<RoomModel> room = this.rr.findById(id);

        return new ResponseEntity<>(room, HttpStatus.OK);

    }
    
    @GetMapping("/filter")
    public ResponseEntity<List<RoomModel>> readAllRoomsWithFilter(@RequestBody @Valid RoomFilterDTO data) {

        List<RoomModel> roomList = this.rr.roomFilter(data.hotel(), data.capacity(), data.price(), data.type());

        return new ResponseEntity<>(roomList, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRoom(@RequestBody RoomModel room, @PathVariable UUID id) {

        try {
            this.rs.update(id, room);
            return new ResponseEntity<>("Updated Room", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable UUID id) {

        try {
            this.rs.delete(id);
            return new ResponseEntity<>("Deleted Room", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
