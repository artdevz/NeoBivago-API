package NeoBivago.dto;

import java.util.UUID;

public record HotelDTO(UUID owner, String name, String address, String city, float score) {}
