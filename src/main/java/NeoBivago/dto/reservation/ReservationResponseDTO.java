package NeoBivago.dto.reservation;

import java.util.Date;
import java.util.UUID;

public record ReservationResponseDTO(UUID id, UUID user, UUID room, Date checkIn, Date checkOut, int nop, int price) {}
