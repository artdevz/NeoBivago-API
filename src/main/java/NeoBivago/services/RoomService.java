package NeoBivago.services;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import NeoBivago.entities.Room;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.repositories.RoomRepository;

@Service
public class RoomService {
    
    @Autowired
    RoomRepository rr;

    public void create(Room room) throws Exception {

        if (this.rr.findByNumber(room.getNumber()) != null) throw new ExistingAttributeException(
            "Room Number is already being used.");

        this.rr.save(room);

    }

    public Room update(UUID id, Map<String, Object> fields) {

        Optional<Room> existingRoom = this.rr.findById(id);

        if (existingRoom.isPresent()) {

            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Room.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingRoom.get(), value);
            });
            return rr.save(existingRoom.get());

        }
        return null;

    }

    public void delete(UUID id) {
        this.rr.deleteById(id);
    }

}
