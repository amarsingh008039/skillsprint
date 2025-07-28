package com.skillsprint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicApiController {
    @GetMapping("/summary")
    public ResponseEntity<String> getSummary(@RequestHeader("x-api-key") String apiKey) {
        return ResponseEntity.ok("You accessed public summary with valid API key.");
    }
}
