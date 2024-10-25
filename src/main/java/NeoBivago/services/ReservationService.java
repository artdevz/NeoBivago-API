package NeoBivago.services;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import NeoBivago.entities.Reservation;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.exceptions.UnauthorizedDateException;
import NeoBivago.repositories.ReservationRepository;

@Service
public class ReservationService {
    
    @Autowired
    ReservationRepository rr;

    public void create(Reservation reservation) throws Exception {

        if ( reservation.getCheckIn().after( reservation.getCheckOut() ) ) throw new UnauthorizedDateException(
            "The Check-In can't be after the Check-Out.");
        

        if ( dateConflicts(reservation.getRoom(), reservation.getCheckIn(), reservation.getCheckOut())) throw new ExistingAttributeException(
            "Date Conflicts Detected :( Please, turn your check-in or check-out date.");

        // To Do: Capacity Limit

        this.rr.save(reservation);

    }

    public Reservation update(UUID id, Map<String, Object> fields) {

        Optional<Reservation> existingReservation = this.rr.findById(id);

        if (existingReservation.isPresent()) {

            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Reservation.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingReservation.get(), value);
            });
            return rr.save(existingReservation.get());

        }
        return null;

    }

    public void delete(UUID id) {
        this.rr.deleteById(id);
    }

    public boolean dateConflicts(UUID roomId, Date checkIn, Date checkOut) {

        // Converting Date to LocalDate:
        LocalDate checkInLD = checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOutLD = checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        List<LocalDate> checkInDates = rr.findAllCheckInByRoom(roomId);
        
        List<LocalDate> checkOutDates = rr.findAllCheckOutByRoom(roomId);

        for (int i = 0; i < checkInDates.size(); i++) {
            LocalDate existingCheckIn = checkInDates.get(i);
            LocalDate existingCheckOut = checkOutDates.get(i);

            // Conflict Verify:
            if ((checkInLD.isBefore(existingCheckOut) && checkOutLD.isAfter(existingCheckIn)) ||
                (checkInLD.isEqual(existingCheckIn) || checkInLD.isEqual(existingCheckOut)) || 
                (checkOutLD.isEqual(existingCheckIn) || checkOutLD.isEqual(existingCheckOut))) {
                return true;
            }
        }

        return false;
        
    }

}
