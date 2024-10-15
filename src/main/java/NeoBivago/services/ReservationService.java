package NeoBivago.services;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import NeoBivago.models.ReservationModel;
import NeoBivago.repositories.ReservationRepository;

@Service
public class ReservationService {
    
    @Autowired
    ReservationRepository rr;

    public void create(ReservationModel reservation) throws Exception {

        this.rr.save(reservation);

    }

    public ReservationModel update(UUID id, Map<String, Object> fields) {

        Optional<ReservationModel> existingReservation = this.rr.findById(id);

        if (existingReservation.isPresent()) {

            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(ReservationModel.class, key);
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

}
