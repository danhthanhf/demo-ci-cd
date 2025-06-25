package me.danhthanhf.distant_class.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.danhthanhf.distant_class.entity.User;
import me.danhthanhf.distant_class.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.security.auth.kerberos.KerberosTicket;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Utility class for handling JWT operations
 * such as token creation, validation, and parsing.
 * @author ThanhND
 * @since 2025-06-20
 * */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;


    // Constants for JWT claims
    public static final String CLAIM_USER_ID  = "userId";
    public static final String CLAIM_ROLE  = "roles";


    public String buildToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim(CLAIM_USER_ID, user.getId())
                .claim(CLAIM_ROLE, user.getRoles())
                .issuedAt(new Date())
                .expiration(new Date((System.currentTimeMillis() + EXPIRATION_TIME)))
                .signWith(getSigningKey())
                .compact();
    }


    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = parseClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // get principal from token to authentication & authorize user
    public UserPrincipal extractUserPrincipal(String token) {
        return extractClaim(token, claims -> UserPrincipal.builder()
                .id(claims.get(CLAIM_USER_ID, Long.class))
                .roles(claims.get(CLAIM_ROLE, List.class))
                .email(claims.getSubject())
                .build()
        );
    }

    // Helper
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
