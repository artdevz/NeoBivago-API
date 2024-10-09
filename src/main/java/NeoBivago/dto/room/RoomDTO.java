package NeoBivago.dto.room;

import java.util.UUID;

import NeoBivago.enums.ERoomType;

public record RoomDTO(UUID hotel, int number, int capacity, int price, ERoomType type) {}
