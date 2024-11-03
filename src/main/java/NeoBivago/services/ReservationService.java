package NeoBivago.services;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import NeoBivago.exceptions.CapacityExceededException;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.exceptions.UnauthorizedDateException;
import NeoBivago.models.Reservation;
import NeoBivago.models.Room;
import NeoBivago.repositories.ReservationRepository;

@Service
public class ReservationService {
    
    @Autowired
    ReservationRepository reservationR;

    public void create(Reservation reservation) throws Exception {

        if ( reservation.getCheckOut().before( new Date() )) throw new UnauthorizedDateException(
            "The Check-In and Check-Out can't be in the Past.");

        if ( reservation.getCheckIn().after( reservation.getCheckOut() ) ) throw new UnauthorizedDateException(
            "The Check-In can't be after the Check-Out.");        

        if ( dateConflicts(reservation.getRoom(), reservation.getCheckIn(), reservation.getCheckOut())) throw new ExistingAttributeException(
            "Date Conflicts Detected :( Please, turn your check-in or check-out date.");
        
        if ( reservation.getNop() > reservation.getRoom().getCapacity() ) throw new CapacityExceededException(
            "Room capacity has been exceeded.");

        this.reservationR.save(reservation);

    }

    public Reservation update(UUID id, Map<String, Object> fields) {

        Optional<Reservation> existingReservation = this.reservationR.findById(id);

        if (existingReservation.isPresent()) {

            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Reservation.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingReservation.get(), value);
            });
            return reservationR.save(existingReservation.get());

        }
        return null;

    }

    public void delete(UUID id) {

        if (!reservationR.findById(id).isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        this.reservationR.deleteById(id);

    }

    private boolean dateConflicts(Room roomId, Date checkIn, Date checkOut) {
        
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
