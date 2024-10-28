package NeoBivago.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import NeoBivago.entities.Hotel;
import NeoBivago.entities.Reservation;
import NeoBivago.entities.Room;
import NeoBivago.entities.User;
import NeoBivago.enums.ERole;
import NeoBivago.enums.ERoom;
import NeoBivago.repositories.ERoleRepository;
import NeoBivago.repositories.ERoomRepository;
import NeoBivago.repositories.HotelRepository;
import NeoBivago.repositories.ReservationRepository;
import NeoBivago.repositories.RoomRepository;
import NeoBivago.repositories.UserRepository;

@Service
public class MappingService {

    @Autowired
    UserRepository userR;

    @Autowired
    HotelRepository hotelR;

    @Autowired
    RoomRepository roomR;

    @Autowired
    ReservationRepository reservationR;

    @Autowired
    ERoleRepository roleER;

    @Autowired
    ERoomRepository roomER;
    
    public User findUserById(UUID id) {

        return userR.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public Hotel findHotelById(UUID id) {

        return hotelR.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public Room findRoomById(UUID id) {

        return roomR.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public Reservation findReservationById(UUID id) {

        return reservationR.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public ERole getRole(String role) {

        return roleER.findByName(role);

    }

    public ERoom getCategory(String category) {

        return roomER.findByCategory(category);

    }

}
