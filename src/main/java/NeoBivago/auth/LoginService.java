package NeoBivago.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import NeoBivago.configs.JwtService;
import NeoBivago.models.User;

@Service
public class LoginService {
    
    @Autowired
    LoginRepository loginR;

    @Autowired
    JwtService jwtS;

    @Autowired
    AuthenticationManager authenticationManager;

    public String signIn(LoginDTO data) {
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.email(), data.password()));
        User user = loginR.findByEmail(data.email()).get();        
        String token = jwtS.generateToken(user);

        return token;

    }

}
