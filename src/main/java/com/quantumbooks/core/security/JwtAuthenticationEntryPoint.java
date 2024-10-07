package com.quantumbooks.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            .withZone(ZoneId.systemDefault());

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> errorObject = new HashMap<>();
        errorObject.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        errorObject.put("message", "Unauthorized access");
        errorObject.put("error", e.getMessage());
        errorObject.put("timestamp", formatter.format(Instant.now()));

        objectMapper.writeValue(httpServletResponse.getOutputStream(), errorObject);
    }
}