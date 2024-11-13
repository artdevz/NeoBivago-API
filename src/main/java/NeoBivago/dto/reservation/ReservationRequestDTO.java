package NeoBivago.dto.reservation;

import java.util.Date;
import java.util.UUID;

public record ReservationRequestDTO(UUID user, UUID room, Date checkIn, Date checkOut, int nop, int price) {}
