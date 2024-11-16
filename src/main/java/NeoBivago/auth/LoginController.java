package NeoBivago.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class LoginController {
    
    final LoginService loginS;

    public LoginController(LoginService loginS) {
        this.loginS = loginS;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO data) {
        
        return new ResponseEntity<>(loginS.login(data), HttpStatus.OK);

    }

}
