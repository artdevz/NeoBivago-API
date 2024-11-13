package NeoBivago.dto.user;

import java.util.Date;
import java.util.UUID;

import NeoBivago.enums.ERole;

public record UserResponseDTO(UUID id, String name, String email, String cpf, Date birthday, ERole role) {}
