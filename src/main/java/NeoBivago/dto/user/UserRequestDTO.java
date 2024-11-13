package NeoBivago.dto.user;

import java.util.Date;

import NeoBivago.enums.ERole;

public record UserRequestDTO(String name, String email, String password, String cpf, Date birthday, ERole role) {}