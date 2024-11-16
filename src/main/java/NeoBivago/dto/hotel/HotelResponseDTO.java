package NeoBivago.dto.hotel;

import java.util.UUID;

public record HotelResponseDTO(UUID id, UUID user, String name, String address, String city, float score) {}
