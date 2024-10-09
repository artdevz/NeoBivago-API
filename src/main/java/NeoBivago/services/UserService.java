package NeoBivago.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import NeoBivago.exceptions.AttributeRegisteredException;
import NeoBivago.exceptions.LenghtException;
import NeoBivago.models.UserModel;
import NeoBivago.repositories.UserRepository;

@Service
public class UserService {
    
    private final int MINLENGHT = 3;
    private final int MAXLENGHT = 32;
    private final int PASSWORDMINLENGHT = 8;

    @Autowired
    UserRepository ur;

    public void create(UserModel user) throws Exception {

        if (this.ur.findByUserEmail(user.getUserEmail()) != null) throw new AttributeRegisteredException("Email is already being used.");
        if (this.ur.findByUserCPF(user.getUserCPF()) != null) throw new AttributeRegisteredException("CPF is already being used.");

        if ( (user.getUserName().length() < MINLENGHT) || (user.getUserName().length() > MAXLENGHT) ) throw new LenghtException("Username must contain between " + MINLENGHT + " and " + MAXLENGHT + " characters.");
        if ( (user.getPassword().length() < PASSWORDMINLENGHT) || (user.getPassword().length() > MAXLENGHT) ) throw new LenghtException("Password must contain between " + PASSWORDMINLENGHT + " and " + MAXLENGHT + " characters.");

        user.setUserPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        this.ur.save(user);

    }

    public void update(UUID id, UserModel user) {

        user.setUserId(id);
        this.ur.save(user);        

    }

    public void delete(UUID id) {
        this.ur.deleteById(id);
    }

}
