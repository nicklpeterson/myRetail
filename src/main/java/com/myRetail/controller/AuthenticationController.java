package com.myRetail.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles requests to the /login endpoint
 *
 * The overrides allow us to respond to failed authentication requests
 * with a 401 status instead of the Spring Security default which is 403
 */
@ControllerAdvice
public class AuthenticationController implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AccessDeniedException accessDeniedException) throws IOException {
        // 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Failed : " + accessDeniedException.getMessage());
    }
}
