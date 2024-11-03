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

@RequestMapping("/auth")
@RestController
public class LoginController {
    
    @Autowired
    LoginService loginS;

    @PostMapping("/signin")
    public ResponseEntity<String> signInAccount(@RequestBody @Valid LoginDTO data) {
        
        try {
            return ResponseEntity.ok(loginS.signIn(data));
        } 
        
        catch (AuthenticationException e) {
            return new ResponseEntity<>("Unathorized", HttpStatus.UNAUTHORIZED);
        }
        
        catch (Exception e) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }

    }

}
