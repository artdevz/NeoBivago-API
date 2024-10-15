package NeoBivago.services;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import NeoBivago.exceptions.AttributeRegisteredException;
import NeoBivago.models.RoomModel;
import NeoBivago.repositories.RoomRepository;

@Service
public class RoomService {
    
    @Autowired
    RoomRepository rr;

    public void create(RoomModel room) throws Exception {

        if (this.rr.findByNumber(room.getNumber()) != null) throw new AttributeRegisteredException("RoomNumber is already being used.");

        this.rr.save(room);

    }

    public RoomModel update(UUID id, Map<String, Object> fields) {

        Optional<RoomModel> existingRoom = this.rr.findById(id);

        if (existingRoom.isPresent()) {

            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(RoomModel.class, key);
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
