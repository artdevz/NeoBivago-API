package NeoBivago.dto.user;

import java.util.Date;

public record UserDTO(String name, String email, String password, String cpf, Date birthday) {}
