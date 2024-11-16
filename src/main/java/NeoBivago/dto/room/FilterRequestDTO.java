package NeoBivago.dto.room;

import NeoBivago.enums.ERoom;

public record FilterRequestDTO(int capacity, int price, ERoom category, String city) {}
