package NeoBivago.dto.room;

import java.util.UUID;

import NeoBivago.enums.ERoom;

public record FilterResponseDTO(UUID hotel, int capacity, int price, ERoom category, String city) {}
