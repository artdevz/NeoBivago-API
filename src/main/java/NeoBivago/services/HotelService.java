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
import NeoBivago.exceptions.LenghtException;
import NeoBivago.models.Hotel;
import NeoBivago.repositories.HotelRepository;

@Service
public class HotelService {
    
    private final int MINLENGHT = 3;
    private final int MAXLENGHT = 32;

    @Autowired
    HotelRepository hotelR;

    public void create(Hotel hotel) throws Exception {
        
        if (this.hotelR.findByName(hotel.getName()) != null) 
            throw new ExistingAttributeException("Hotel is already being used.");

        if ( (hotel.getName().length() < MINLENGHT) || (hotel.getName().length() > MAXLENGHT) ) 
            throw new LenghtException("Hotel Name must contain between " + MINLENGHT + " and " + MAXLENGHT + " characters.");
        
        if ( (hotel.getAddress().length() < MINLENGHT) || (hotel.getAddress().length() > MAXLENGHT) ) 
            throw new LenghtException("Hotel Address must contain between " + MINLENGHT + " and " + MAXLENGHT + " characters.");
        
        if ( (hotel.getCity().length() < MINLENGHT) || (hotel.getCity().length() > MAXLENGHT) ) 
            throw new LenghtException("Hotel City must contain between " + MINLENGHT + " and " + MAXLENGHT + " characters.");

        this.hotelR.save(hotel);

    }

    public Hotel update(UUID id, Map<String, Object> fields) {

        Optional<Hotel> existingHotel = this.hotelR.findById(id);

        if (existingHotel.isPresent()) {

            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Hotel.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingHotel.get(), value);
            });
            return hotelR.save(existingHotel.get());

        }
        return null;

    }
    public void delete(UUID id) {

        if (!hotelR.findById(id).isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        this.hotelR.deleteById(id);

    }

}
