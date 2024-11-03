package NeoBivago.services;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import NeoBivago.exceptions.UnauthorizedDateException;
import NeoBivago.models.User;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.exceptions.LenghtException;
import NeoBivago.repositories.UserRepository;

@Service
public class UserService {
    
    private final int MIN_LENGHT = 3;
    private final int MAX_LENGHT = 32;
    private final int PASSWORD_MIN_LENGHT = 8;
    private final int MINIMUM_AGE = 18;

    @Autowired
    UserRepository userR;

    public void create(User user) throws Exception {

        if (this.userR.findByEmail(user.getEmail()) != null) throw new ExistingAttributeException(
            "Email is already being used.");

        if (this.userR.findByCpf(user.getCpf()) != null) throw new ExistingAttributeException(
            "CPF is already being used.");

        if ( (user.getName().length() < MIN_LENGHT) || (user.getName().length() > MAX_LENGHT) ) throw new LenghtException(
            "Username must contain between " + MIN_LENGHT + " and " + MAX_LENGHT + " characters.");
        
        if ( (user.getPassword().length() < PASSWORD_MIN_LENGHT) || (user.getPassword().length() > MAX_LENGHT) ) throw new LenghtException(
            "Password must contain between " + PASSWORD_MIN_LENGHT + " and " + MAX_LENGHT + " characters.");

        if ( user.getBirthday().after(minimumDate()) ) throw new UnauthorizedDateException(
            "User must be at least 18 years old.");
        

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        this.userR.save(user);

    }

    public User update(UUID id, Map<String, Object> fields) {

        Optional<User> existingUser = this.userR.findById(id);

        if (existingUser.isPresent()) {

            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingUser.get(), value);
            });
            return userR.save(existingUser.get());

        }
        return null;

    }

    public void delete(UUID id) {

        if (!userR.findById(id).isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        this.userR.deleteById(id);

    }

    private Date minimumDate() {
    
        LocalDateTime ldate = LocalDateTime.now().minusYears(MINIMUM_AGE);
        java.util.Date date = new java.util.Date();
        date = Date.from(ldate.atZone(ZoneId.systemDefault()).toInstant());
        return new java.sql.Date(date.getTime());

    }

}
