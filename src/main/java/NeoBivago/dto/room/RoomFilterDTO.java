package NeoBivago.dto.room;

import java.util.UUID;

import NeoBivago.enums.ERoomType;
import NeoBivago.models.RoomModel;

public record RoomFilterDTO(UUID hotel, int number, int capacity, int price, ERoomType type) {
    public RoomFilterDTO(RoomModel room) {
        this(room.getHotelId(), room.getRoomNumber(), room.getRoomCapacity(), room.getRoomPrice(), room.getRoomType());
    }
}
