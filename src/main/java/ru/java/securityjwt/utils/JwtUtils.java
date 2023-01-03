package ru.java.securityjwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.java.securityjwt.dto.UserDto;
import ru.java.securityjwt.exception.UserServiceException;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwtexpiry-inmilliseconds}")
    int expiryTime;

    @Value("${app.jwt-secrets}")
    String secretKey;

    public String generateToken(UserDto user) throws Exception {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new ServletException("Please enter a valid username and password");
        }
        Date current = new Date();
        //ExpiryTime is set to 15 min from issued date
        Date expiryDate = new Date(current.getTime() + expiryTime);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(current)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        Claims claim = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claim.getSubject();
    }

    public boolean validateToken(String token) throws UserServiceException {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException exception) {
            throw UserServiceException.CODE.INVALID_ACCESS.get(exception);
        } catch (MalformedJwtException exception) {
            throw UserServiceException.CODE.TOKEN_IS_NOT_AUTHORIZED.get(exception);
        } catch (ExpiredJwtException exception) {
            throw UserServiceException.CODE.TOKEN_IS_EXPIRED.get(exception);
        } catch (UnsupportedJwtException exception) {
            throw UserServiceException.CODE.TOKEN_IS_UNSUPPORTED.get(exception);
        } catch (IllegalArgumentException exception) {
            throw UserServiceException.CODE.ILLEGAL_ARGUMENTS_RECEIVED.get(exception);
        }
    }
}
