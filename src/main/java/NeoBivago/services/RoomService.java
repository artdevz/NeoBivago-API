package NeoBivago.services;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.models.Room;
import NeoBivago.repositories.RoomRepository;

@Service
public class RoomService {
    
    @Autowired
    RoomRepository roomR;

    public void create(Room room) throws Exception {

        if (this.roomR.findByNumber(room.getNumber()) != null) throw new ExistingAttributeException(
            "Room Number is already being used.");

        this.roomR.save(room);

    }

    public Room update(UUID id, Map<String, Object> fields) {

        Optional<Room> existingRoom = this.roomR.findById(id);

        if (existingRoom.isPresent()) {

            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Room.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingRoom.get(), value);
            });
            return roomR.save(existingRoom.get());

        }
        return null;

    }

    public void delete(UUID id) {

        if (!roomR.findById(id).isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        this.roomR.deleteById(id);

    }

}
