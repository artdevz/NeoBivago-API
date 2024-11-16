package NeoBivago.services;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import NeoBivago.dto.reservation.ReservationRequestDTO;
import NeoBivago.dto.reservation.ReservationResponseDTO;
import NeoBivago.exceptions.CapacityExceededException;
import NeoBivago.exceptions.UnauthorizedDateException;
import NeoBivago.models.Reservation;
import NeoBivago.models.Room;
import NeoBivago.repositories.ReservationRepository;

@Service
public class ReservationService {
    
    private final ReservationRepository reservationR;
    private final MappingService mappingS;

    public ReservationService(ReservationRepository reservationR, MappingService mappingS) {
        this.reservationR = reservationR;
        this.mappingS = mappingS;
    }

    public void create(ReservationRequestDTO data) throws Exception {

        validateCheckInAndCheckOut(data.checkIn(), data.checkOut());
        validateRoomCapacity(mappingS.findRoomById(data.room()), data.nop());
        validateDateConflict(mappingS.findRoomById(data.room()), data.checkIn(), data.checkOut());

        Reservation reservation = new Reservation(
            mappingS.findUserById(data.user()),
            mappingS.findRoomById(data.room()),
            data.checkIn(),
            data.checkOut(),
            data.nop(),
            data.price()
        );

        this.reservationR.save(reservation);

    }

    public List<ReservationResponseDTO> readAll() {

        return reservationR.findAll().stream()
            .map(reservation -> new ReservationResponseDTO(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getRoom().getId(),
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                reservation.getNop(),
                reservation.getPrice()
            ))
            .collect(Collectors.toList());
    }
    
    public ReservationResponseDTO readById(UUID id) {

        Reservation reservation = reservationR.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        return new ReservationResponseDTO(
            reservation.getId(),
            reservation.getUser().getId(),
            reservation.getRoom().getId(),
            reservation.getCheckIn(),
            reservation.getCheckOut(),
            reservation.getNop(),
            reservation.getPrice()
        );
    }

    public Reservation update(UUID id, Map<String, Object> fields) {

        Optional<Reservation> existingReservation = reservationR.findById(id);
    
        if (existingReservation.isPresent()) {
            Reservation reservation = existingReservation.get();
    
            fields.forEach((key, value) -> {
                switch (key) {                                     

                    default:
                        Field field = ReflectionUtils.findField(Reservation.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, reservation, value);
                        }
                        break;
                }
            }); 
                       
            return reservationR.save(reservation);
        } 
        
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
    }

    public void delete(UUID id) {

        if (!reservationR.findById(id).isPresent()) 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        reservationR.deleteById(id);

    }

    private void validateCheckInAndCheckOut(Date checkIn, Date checkOut) {
        
        if (checkOut.before(new Date())) {
            throw new UnauthorizedDateException("The Check-In and Check-Out can't be in the Past.");
        }

        if (checkIn.after(checkOut)) {
            throw new UnauthorizedDateException("The Check-In can't be after the Check-Out.");
        }

    }

    private void validateRoomCapacity(Room room, int nop) {

        if (nop > room.getCapacity()) {
            throw new CapacityExceededException("Room capacity has been exceeded.");
        }

    }

    private boolean validateDateConflict(Room roomId, Date checkIn, Date checkOut) {
        
        List<Date> checkInDates = reservationR.findAllCheckInByRoom(roomId.getId());
        
        List<Date> checkOutDates = reservationR.findAllCheckOutByRoom(roomId.getId());
        
        for (int i = 0; i < checkInDates.size(); i++) {
            Date existingCheckIn = checkInDates.get(i);
            Date existingCheckOut = checkOutDates.get(i);

            // Conflict Verify:
            if ((checkIn.compareTo(existingCheckOut) < 0 && checkOut.compareTo(existingCheckIn) > 0) ||
                (checkIn.compareTo(existingCheckIn) == 0 || checkIn.compareTo(existingCheckOut) == 0) || 
                (checkOut.compareTo(existingCheckIn) == 0 || checkOut.compareTo(existingCheckOut) == 0)) {
                return true;
            }
        }
        
        return false;
        
    }

}
