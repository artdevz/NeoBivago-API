package NeoBivago.configs;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import NeoBivago.models.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    public String generateToken(UserModel user) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", user.getName());
        extraClaims.put("id", user.getId().toString());
        extraClaims.put("role", user.getRole());

        return Jwts.
            builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(new Date().getTime() + 3600000 * JwtConfig.EXPIRATEDTOKENTIME))
            .signWith(getSigningKey(), JwtConfig.SIGNATURE)
            .compact();

    }

    private Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String USERNAME = extractUsername(token);
        System.out.println("USERNAME: " + USERNAME);
        System.out.println("getUsername: " + user.getUsername());
        return (USERNAME.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JwtConfig.SECRETKEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

}
