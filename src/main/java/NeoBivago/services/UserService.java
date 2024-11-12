package NeoBivago.services;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import NeoBivago.exceptions.UnauthorizedDateException;
import NeoBivago.models.User;
import NeoBivago.dto.UserRequestDTO;
import NeoBivago.dto.UserResponseDTO;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.exceptions.InvalidAttributeException;
import NeoBivago.exceptions.LenghtException;
import NeoBivago.repositories.UserRepository;

@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    private final int MIN_LENGHT = 3;
    private final int MAX_LENGHT = 32;
    private final int PASSWORD_MIN_LENGHT = 8;
    private final int MINIMUM_AGE = 18;

    private final UserRepository userR;
    
    private final MappingService mappingS;

    public UserService(UserRepository userR, MappingService mappingS) {
        this.userR = userR;
        this.mappingS = mappingS;
    }

    public void create(UserRequestDTO data) {

        validateEmail(data.email());
        validateCPF(data.cpf());
        validateAge(data.birthday());
        validateName(data.name());
        validatePassword(data.password());

        User user = new User(
            data.name(),
            data.email(),
            passwordEncoder.encode(data.password()),
            data.cpf(),
            data.birthday(),
            mappingS.getRole(data.role().getName())
        );
        
        this.userR.save(user);

    }

    public List<UserResponseDTO> readAll() {

        return this.userR.findAll().stream()
            .map(user -> new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getBirthday(),
                user.getRole()
            ))
            .collect(Collectors.toList());

    }

    public UserResponseDTO readById(UUID id) {
        User user = userR.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCpf(),
            user.getBirthday(),
            user.getRole()
        );
    }

    public User update(UUID id, Map<String, Object> fields) {

        Optional<User> existingUser = this.userR.findById(id);
    
        if (existingUser.isPresent()) {
            User user = existingUser.get();
    
            fields.forEach((key, value) -> {
                switch (key) {
                    case "name":
                        String name = (String) value;
                        validateName(name);
                        user.setName(name);
                        break;
                    case "password":
                        String password = (String) value;
                        validatePassword(password);
                        user.setPassword(passwordEncoder.encode(password));
                        break;            
                    case "role":
                        String roleName = (String) value;
                        user.setRole(mappingS.getRole(roleName));
                        break;
                    default:
                        Field field = ReflectionUtils.findField(User.class, key);
                        if (field != null) {
                            field.setAccessible(true);
                            ReflectionUtils.setField(field, user, value);
                        }
                        break;
                }
            });
            
            return userR.save(user);
        } 
        
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        
    }

    public void delete(UUID id) {

        if (!userR.findById(id).isPresent()) 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        this.userR.deleteById(id);

    }

    private void validateEmail(String email) {

        if (this.userR.findByEmail(email) != null) 
            throw new ExistingAttributeException("Email is already being used.");

    }

    private void validateCPF(String cpf) {

        if (!validateCpfFormat(cpf))
            throw new InvalidAttributeException("Invalid CPF format.");

        if (this.userR.findByCpf(cpf) != null) 
            throw new ExistingAttributeException("CPF is already being used.");

    }

    private boolean validateCpfFormat(String cpf) {        
        cpf = cpf.replaceAll("\\D", "");
        
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;
        
        try {
            int tenthDigit = calculateDigit(cpf, 10);
            int eleventhDigit = calculateDigit(cpf, 11);

            return cpf.charAt(9) == (char) (tenthDigit + '0') && cpf.charAt(10) == (char) (eleventhDigit + '0');
        } 
        
        catch (Exception e) {
            return false;
        }
    }

    private int calculateDigit(String cpf, int weight) {
        int sum = 0;
        
        for (int i = 0; i < weight - 1; i++) sum += (cpf.charAt(i) - '0') * (weight - i);
        
        int remainder = 11 - (sum % 11);
        return (remainder > 9) ? 0 : remainder;
    }

    private void validateAge(Date birthday) {

        if ( birthday.after(minimumDate()) ) 
            throw new UnauthorizedDateException("User must be at least 18 years old.");

    }

    private Date minimumDate() {
    
        LocalDateTime ldate = LocalDateTime.now().minusYears(MINIMUM_AGE);
        java.util.Date date = new java.util.Date();
        date = Date.from(ldate.atZone(ZoneId.systemDefault()).toInstant());
        return new java.sql.Date(date.getTime());

    }    

    private void validateName(String name) {

        if ( (name.length() < MIN_LENGHT) || (name.length() > MAX_LENGHT) ) 
            throw new LenghtException("Username must contain between " + MIN_LENGHT + " and " + MAX_LENGHT + " characters.");

    }

    private void validatePassword(String password) {

        if ( (password.length() < PASSWORD_MIN_LENGHT) || (password.length() > MAX_LENGHT) ) 
            throw new LenghtException("Password must contain between " + PASSWORD_MIN_LENGHT + " and " + MAX_LENGHT + " characters.");

    }    

}
