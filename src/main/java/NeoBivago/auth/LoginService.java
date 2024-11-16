package NeoBivago.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import NeoBivago.configs.JwtService;
import NeoBivago.models.User;

@Service
public class LoginService {
    
    final LoginRepository loginR;

    final JwtService jwtS;

    final AuthenticationManager authenticationManager;

    public LoginService(LoginRepository loginR, JwtService jwtS, AuthenticationManager authenticationManager) {
        this.loginR = loginR;
        this.jwtS = jwtS;
        this.authenticationManager = authenticationManager;
    }

    public String login(LoginRequestDTO data) {
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.email(), data.password()));
                
        User user = loginR.findByEmail(data.email()).get();                    
        return jwtS.generateToken(user);
        
    }

}
