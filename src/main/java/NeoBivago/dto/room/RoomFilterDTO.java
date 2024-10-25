package NeoBivago.dto.room;

import NeoBivago.enums.ERoomType;

public record RoomFilterDTO(int capacity, int price, ERoomType type, String city) {    
}
