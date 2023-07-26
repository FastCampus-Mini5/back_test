package com.example.server._core.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.server.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    public static final Long EXP = 1000L * 60 * 60 * 48;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";

    @Value("${secret-key}")
    private static String secretKey;

    private final Environment environment;

    @PostConstruct
    private void init() {
        secretKey = environment.getProperty("secret-key");
    }

    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("username", user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .sign(Algorithm.HMAC512(secretKey));
        log.info("JWT created: " + jwt);
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verify(String jwt) {
        log.info("JWT verify: " + jwt);
        return JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(jwt);
    }
}
