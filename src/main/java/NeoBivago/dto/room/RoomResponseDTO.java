package NeoBivago.dto.room;

import java.util.UUID;

import NeoBivago.enums.ERoom;
import NeoBivago.models.Hotel;

public record RoomResponseDTO(UUID id, Hotel hotel, int number, int capacity, int price, ERoom category) {}
