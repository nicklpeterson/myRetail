package com.myRetail.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.myRetail.exceptions.UserNotFoundException;
import com.myRetail.models.User;
import com.myRetail.service.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.myRetail.security.SecurityConstants.*;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // JWT tokens will expire after 15 minutes
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userService;

    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            final User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        final String username = ((org.springframework.security.core.userdetails.User)  auth.getPrincipal()).getUsername();
        try {
            final User user = userService.getUserByUsername(username);
            final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            final String token = JWT.create()
                    .withSubject(objectWriter.writeValueAsString(user))
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(HMAC512(SECRET.getBytes()));
            res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
            res.setHeader(ACCESS_CONTROL_HEADER, HEADER_STRING);
        } catch (UserNotFoundException e) {
            throw new ServletException("Failed to load user after a successful authentication");
        }
    }
}
