package NeoBivago.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NeoBivago.exceptions.AttributeRegisteredException;
import NeoBivago.exceptions.LenghtException;
import NeoBivago.models.HotelModel;
import NeoBivago.repositories.HotelRepository;

@Service
public class HotelService {
    
    private final int MINLENGHT = 3;
    private final int MAXLENGHT = 32;

    @Autowired
    HotelRepository hr;

    public void create(HotelModel hotel) throws Exception {
        
        if (this.hr.findByHotelName(hotel.getHotelName()) != null) throw new AttributeRegisteredException("Hotel is already being used.");

        if ( (hotel.getHotelName().length() < MINLENGHT) || (hotel.getHotelName().length() > MAXLENGHT) ) throw new LenghtException("HotelName must contain between " + MINLENGHT + " and " + MAXLENGHT + " characters.");
        if ( (hotel.getHotelAddress().length() < MINLENGHT) || (hotel.getHotelAddress().length() > MAXLENGHT) ) throw new LenghtException("HotelAddress must contain between " + MINLENGHT + " and " + MAXLENGHT + " characters.");
        if ( (hotel.getHotelCity().length() < MINLENGHT) || (hotel.getHotelCity().length() > MAXLENGHT) ) throw new LenghtException("HotelCity must contain between " + MINLENGHT + " and " + MAXLENGHT + " characters.");

        this.hr.save(hotel);

    }

    public void update(UUID id, HotelModel hotel) {
        
        hotel.setHotelId(id);
        this.hr.save(hotel);

    }

    public void delete(UUID id) {
        this.hr.deleteById(id);
    }

}
