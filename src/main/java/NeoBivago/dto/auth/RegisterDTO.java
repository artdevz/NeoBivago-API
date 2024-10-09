package NeoBivago.dto.auth;

import java.util.Date;

public record RegisterDTO(String name, String email, String password, String cpf, Date birthday) {}
