package NeoBivago.configs.jwt;

import io.jsonwebtoken.SignatureAlgorithm;

public class JwtConfig {

    public static String SECRETKEY = "SUPERSECRETKEYNOBODYKNOWHAHAHAHHAHAHAHAHAHAHHAHAH";
    public static final SignatureAlgorithm SIGNATURE = SignatureAlgorithm.HS256;
    public static final int EXPIRATEDTOKENTIME = 1; 
    
}
