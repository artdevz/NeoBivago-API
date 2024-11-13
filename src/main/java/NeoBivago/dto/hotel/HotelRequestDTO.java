package NeoBivago.dto.hotel;

import java.util.UUID;

public record HotelRequestDTO(UUID owner, String name, String address, String city, float score) {}
