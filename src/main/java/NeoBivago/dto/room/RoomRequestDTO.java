package NeoBivago.dto.room;

import java.util.UUID;

import NeoBivago.enums.ERoom;

public record RoomRequestDTO(UUID hotel, int number, int capacity, int price, ERoom category) {}
