package com.example.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface GptService {
    ResponseEntity<?> getAssistantMsg(String userMsg) throws JsonProcessingException;
}
