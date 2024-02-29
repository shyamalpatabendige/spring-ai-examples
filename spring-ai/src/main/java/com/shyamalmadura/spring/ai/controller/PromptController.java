package com.shyamalmadura.spring.ai.controller;

import com.shyamalmadura.spring.ai.application.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @GetMapping(value = "/starter")
    public ResponseEntity<String> generateSentenceStarter(
            @RequestParam("language") String language, @RequestParam("topic") String topic) {
        return ResponseEntity.ok(promptService.generateSentences(language, topic));
    }
}