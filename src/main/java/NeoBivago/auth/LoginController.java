package NeoBivago.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RequestMapping("/api/auth")
@RestController
public class LoginController {
    
    @Autowired
    LoginService ls;


    // @PostMapping("/signup")
    // public ResponseEntity<Object> signUpAccount(@RequestBody @Valid RegisterDTO data) {

    //     UserModel newUser = new UserModel(data.name(), data.email(), data.password(), data.cpf(), data.birthday() );

    //     try {
    //         this.us.create(newUser);
    //         return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    //     }   

    // }

    @PostMapping("/signin")
    public ResponseEntity<String> signInAccount(@RequestBody @Valid LoginDTO data) {
        
        try {
            return ResponseEntity.ok(ls.signIn(data));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Unathorized", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }

    }

    // @PostMapping("/signout")

}
