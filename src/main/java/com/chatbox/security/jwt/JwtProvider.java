package com.chatbox.security.jwt;

import com.chatbox.security.userprincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret = "quockhanh01091@gmail.com";
    private int jwtExpiration = 86400;

    public String createToken(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setClaims(new HashMap<String, Object>())
                .setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message {}", e);
//            throw new RuntimeException("Invalid JWT signature -> Message {}");
        } catch (MalformedJwtException e) {
            logger.error("Invalid format Token -> Message {}", e);
//            throw new RuntimeException("Invalid format Token -> Message {}");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token -> Message {}", e);
//            throw new RuntimeException("Expired JWT Token -> Message {}");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT Token -> Message {}", e);
//            throw new RuntimeException("Unsupported JWT Token -> Message {}");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message {}", e);
//            throw new RuntimeException("JWT claims string is empty -> Message {}");
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return username;
    }

}
