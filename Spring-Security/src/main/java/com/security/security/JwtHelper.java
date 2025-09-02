package com.security.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Password;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@Component
public class JwtHelper {
    //requirement
    //1. validity should be in ms
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    // 2. Secret key
    public static final String SECRET_KEY = "gfhbjkljhgvbbbjdbfshfsjbfhbsghjfhshfhjsbfhskkkkkkkkkkkkkkkkkkkkkkkkkkkkkjkkkkkkkkkkkkkkkkkjhfsdffffffffffsdsdfsdadsfsfsafsfd";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getCaimFromToken(token, Claims::getSubject);
    }

    public <T> T getCaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getPayload();

//        SignatureAlgorithm hs512 = SignatureAlgorithm.HS512;
//        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), hs512.getJcaName());
//        return Jwts.parser().verifyWith(secretKeySpec).build().parseClaimsJws(token).getPayload();
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getCaimFromToken(token, Claims::getExpiration);
    }


    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }
}
