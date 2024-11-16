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

import NeoBivago.dto.room.RoomRequestDTO;
import NeoBivago.dto.room.RoomResponseDTO;
import NeoBivago.dto.room.FilterRequestDTO;
import NeoBivago.dto.room.FilterResponseDTO;
import NeoBivago.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RequestMapping("/room")
@RestController
public class RoomController {   
    
    private final RoomService roomS;   

    public RoomController(RoomService roomS) {
        this.roomS = roomS;        
    }

    @Operation(
        summary = "Create a new room",
        description = "Create a new room in the NeoBivago system.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Room created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid room data."),
            @ApiResponse(responseCode = "409", description = "Room with the same name or attributes already exists.")
        }
    )
    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody @Valid RoomRequestDTO data) throws Exception {
       
        roomS.create(data);
        return new ResponseEntity<>("Created Room", HttpStatus.CREATED);
        
    }

    @Operation(
        summary = "Get all rooms",
        description = "Retrieve a list of all rooms registered in the NeoBivago system.",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of all rooms retrieved successfully."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
        }
    )
    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> readAllRooms() {        

        return new ResponseEntity<>(roomS.readAll(), HttpStatus.OK);

    }

    @Operation(
        summary = "Get a room by ID",
        description = "Retrieve details of a specific room by its ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Room found."),
            @ApiResponse(responseCode = "404", description = "Room not found.")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> findRoomById(@PathVariable UUID id) {

        return new ResponseEntity<>(roomS.readById(id), HttpStatus.OK);

    }

    @Operation(
        summary = "Filter rooms",
        description = "Return a list of rooms filtered by certain criteria.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Rooms found based on filter criteria."),
            @ApiResponse(responseCode = "400", description = "Invalid filter data.")
        }
    )
    @GetMapping("/filter")
    public ResponseEntity<List<FilterResponseDTO>> readRoomFilter(@RequestBody @Valid FilterRequestDTO data) {
   
        return new ResponseEntity<>(roomS.filter(data), HttpStatus.OK);

    }

    @Operation(
        summary = "Update room details",
        description = "Update room details by providing its ID and the fields to update.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully."),
            @ApiResponse(responseCode = "404", description = "Room not found."),
            @ApiResponse(responseCode = "400", description = "Invalid fields or data.")
        }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateRoom(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {
        
        this.roomS.update(id, fields);
        return new ResponseEntity<>("Updated Room", HttpStatus.OK);
        
    }

    @Operation(
        summary = "Delete a room",
        description = "Delete a room from the NeoBivago system by providing its ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Room deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Room not found.")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable UUID id) {
        
        this.roomS.delete(id);
        return new ResponseEntity<>("Deleted Room", HttpStatus.OK);

    }

}
