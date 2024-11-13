package NeoBivago.dto.hotel;

import java.util.UUID;

import NeoBivago.models.User;

public record HotelResponseDTO(UUID id, User user, String name, String address, String city, float score) {}
