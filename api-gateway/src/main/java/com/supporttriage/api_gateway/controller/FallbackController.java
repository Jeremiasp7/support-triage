package com.supporttriage.api_gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class FallbackController {

    private static final Logger log = LoggerFactory.getLogger(FallbackController.class);

    @RequestMapping("/fallback")
    public ResponseEntity<Map<String, String>> ticketServiceFallback(HttpServletRequest request) {
        log.warn("[Gateway Fallback] ticket-service indisponivel - URI: {}", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                "status", "503",
                "service", "ticket-service",
                "message", "O servico de tickets esta temporariamente indisponivel. Tente novamente em instantes."
        ));
    }
}