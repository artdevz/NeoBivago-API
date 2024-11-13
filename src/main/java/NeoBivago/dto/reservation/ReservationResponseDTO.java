package NeoBivago.dto.reservation;

import java.util.Date;
import java.util.UUID;

import NeoBivago.models.Room;
import NeoBivago.models.User;

public record ReservationResponseDTO(UUID id, User user, Room room, Date checkIn, Date checkOut, int nop, int price) {}
