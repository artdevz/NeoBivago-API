package NeoBivago.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import NeoBivago.dto.hotel.HotelRequestDTO;
import NeoBivago.dto.hotel.HotelResponseDTO;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.exceptions.LenghtException;
import NeoBivago.models.Hotel;
import NeoBivago.repositories.HotelRepository;

@Service
public class HotelService {
    
    private final int MIN_LENGTH = 3;
    private final int MAX_LENGTH = 32;

    private final HotelRepository hotelR;
    private final MappingService mappingS;

    public HotelService(HotelRepository hotelR, MappingService mappingS) {
        this.hotelR = hotelR;
        this.mappingS = mappingS;
    }

    public void create(HotelRequestDTO data) throws Exception {
        
        validateName(data.name());
        validateAddress(data.address());
        validateCity(data.city());     

        Hotel hotel = new Hotel(
            mappingS.findUserById(data.owner()),
            data.name(),
            data.address(),
            data.city(),
            data.score()           
        );

        hotelR.save(hotel);
    }

    public List<HotelResponseDTO> readAll() {

        return hotelR.findAll().stream()
            .map(hotel -> new HotelResponseDTO(
                hotel.getId(),
                hotel.getOwner(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getCity(),
                hotel.getScore()
            ))
            .collect(Collectors.toList());
    }

    public HotelResponseDTO readById(UUID id) {

        Hotel hotel = hotelR.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));

        return new HotelResponseDTO(
            hotel.getId(),
            hotel.getOwner(),
            hotel.getName(),
            hotel.getAddress(),
            hotel.getCity(),
            hotel.getScore()
        );
    }

    public Hotel update(UUID id, Map<String, Object> fields) {

        Optional<Hotel> existingHotel = hotelR.findById(id);
    
        if (existingHotel.isPresent()) {
            Hotel hotel = existingHotel.get();
    
            fields.forEach((key, value) -> {
                switch (key) {

                    case "name":
                        String name = (String) value;
                        validateName(name);
                        hotel.setName(name);
                        break;

                    case "address":
                        String address = (String) value;
                        validateAddress(address);
                        hotel.setAddress(address);
                        break;

                    case "city":
                        String city = (String) value;
                        validateCity(city);
                        hotel.setCity(city);
                        break;

                    default:
                        Field field = ReflectionUtils.findField(Hotel.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, hotel, value);
                        }
                        break;
                }
            }); 
                       
            return hotelR.save(hotel);
        } 
        
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
    }

    public void delete(UUID id) {

        if (!hotelR.findById(id).isPresent()) 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        hotelR.deleteById(id);
    }

    private void validateName(String data) {

        if ( (data.length() < MIN_LENGTH) || (data.length() > MAX_LENGTH) ) 
            throw new LenghtException("Hotel Name must contain between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters.");

        if (hotelR.findByName(data) != null) 
            throw new ExistingAttributeException("Hotel is already being used.");

    }

    private void validateAddress(String data) {

        if ( (data.length() < MIN_LENGTH) || (data.length() > MAX_LENGTH) ) 
            throw new LenghtException("Hotel Address must contain between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters.");

    }

    private void validateCity(String data) {

        if ( (data.length() < MIN_LENGTH) || (data.length() > MAX_LENGTH) ) 
            throw new LenghtException("Hotel City must contain between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters.");

    }

}
