package NeoBivago.dto;

import java.util.Date;
import java.util.UUID;

public record ReservationDTO(UUID user, UUID room, Date checkIn, Date checkOut, int nop, int price) {}
