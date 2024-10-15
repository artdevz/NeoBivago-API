package NeoBivago.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NeoBivago.dto.auth.LoginDTO;
import NeoBivago.dto.auth.LoginResponseDTO;
import NeoBivago.dto.auth.RegisterDTO;
import NeoBivago.models.UserModel;
import NeoBivago.repositories.UserRepository;
import NeoBivago.services.TokenService;
import NeoBivago.services.UserService;
import jakarta.validation.Valid;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository ur;

    @Autowired
    UserService us;

    @Autowired 
    TokenService ts;

    @PostMapping("/signup")
    public ResponseEntity<Object> signUpAccount(@RequestBody @Valid RegisterDTO data) {

        UserModel newUser = new UserModel(data.name(), data.email(), data.password(), data.cpf(), data.birthday() );

        try {
            this.us.create(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }   

    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> signInAccount(@RequestBody @Valid LoginDTO data) {
        
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = ts.generateToken( (UserModel) auth.getPrincipal());

        return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.OK);

    }

    // @PostMapping("/signout")

}
