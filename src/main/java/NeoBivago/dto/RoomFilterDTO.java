package NeoBivago.dto;

import NeoBivago.enums.ERoom;

public record RoomFilterDTO(int capacity, int price, ERoom category, String city) {    
}
