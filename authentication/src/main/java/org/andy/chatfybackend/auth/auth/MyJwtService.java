package org.andy.chatfybackend.auth.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class MyJwtService {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // or HS384 or HS512

    public String generateToken(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("username", user.getUsername())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + 3600000))
                .signWith(key)
                .compact();
    }

    /**
     * ExpiredJwtException,
     * UnsupportedJwtException,
     * MalformedJwtException,
     * SignatureException,
     * IllegalArgumentException;
     * */
    public User parseToken(String token){
        Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        String username = claims.getBody().get("username", String.class);
        String email = claims.getBody().getSubject();
        Long id = claims.getBody().get("id", Long.class);
        Role role = Role.valueOf(claims.getBody().get("role", String.class));
        return new User(username, email, id, role);
    }

}
