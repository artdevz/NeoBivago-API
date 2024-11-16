package NeoBivago.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import NeoBivago.dto.room.FilterRequestDTO;
import NeoBivago.dto.room.FilterResponseDTO;
import NeoBivago.dto.room.RoomRequestDTO;
import NeoBivago.dto.room.RoomResponseDTO;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.models.Room;
import NeoBivago.repositories.RoomRepository;

@Service
public class RoomService {
    
    @Autowired
    private final RoomRepository roomR;
    private final MappingService mappingS;

    public RoomService(RoomRepository roomR, MappingService mappingS) {
        this.roomR = roomR;
        this.mappingS = mappingS;
    }

    public void create(RoomRequestDTO data) {

        validateNumber(data.number());

        Room room = new Room(
            mappingS.findHotelById(data.hotel()),
            data.number(),
            data.capacity(),
            data.price(),
            mappingS.getCategory(data.category().getCategory())
        );

        roomR.save(room);

    }

    public List<RoomResponseDTO> readAll() {

        return roomR.findAll().stream()
            .map(room -> new RoomResponseDTO(
                room.getId(),
                room.getHotel().getId(),
                room.getNumber(),
                room.getCapacity(),
                room.getPrice(),
                room.getCategory()
            ))
            .collect(Collectors.toList());
    }

    public RoomResponseDTO readById(UUID id) {

        Room room = roomR.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        return new RoomResponseDTO(
            room.getId(),
            room.getHotel().getId(),
            room.getNumber(),
            room.getCapacity(),
            room.getPrice(),
            room.getCategory()
        );
    }

    public Room update(UUID id, Map<String, Object> fields) {

        Optional<Room> existingRoom = roomR.findById(id);
    
        if (existingRoom.isPresent()) {
            Room room = existingRoom.get();
    
            fields.forEach((key, value) -> {
                switch (key) {

                    case "number":
                        Integer number = (Integer) value;
                        validateNumber(number);
                        room.setNumber(number);
                        break;                    

                    default:
                        Field field = ReflectionUtils.findField(Room.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, room, value);
                        }
                        break;
                }
            }); 
                       
            return roomR.save(room);
        } 
        
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
    }

    public void delete(UUID id) {

        if (!roomR.findById(id).isPresent()) 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
        roomR.deleteById(id);

    }

    public List<FilterResponseDTO> filter(FilterRequestDTO data) {
        
        List<FilterResponseDTO> roomCity = new ArrayList<>();

        List<Room> roomList = roomR.roomFilter(
            data.capacity(), data.price(), 
                mappingS.getId(
                    data.category().getCategory()).intValue());
        
        for (Room room : roomList) {
            if (room.getHotel().getCity().contains(data.city())) {

                roomCity.add(
                    new FilterResponseDTO(
                        room.getHotel().getId(), 
                        room.getCapacity(),
                        room.getPrice(),
                        room.getCategory(),
                        room.getHotel().getCity())
                );
            }
        }        

        return roomCity.stream().collect(Collectors.toList());        

    }

    private void validateNumber(int number) {

        if (roomR.findByNumber(number) != null) 
            throw new ExistingAttributeException("Room Number is already being used.");

    } 

}
