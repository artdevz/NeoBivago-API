package NeoBivago.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void update(UUID id, RoomModel room) {
        
        room.setId(id);
        this.rr.save(room);

    }

    public void delete(UUID id) {
        this.rr.deleteById(id);
    }

}
