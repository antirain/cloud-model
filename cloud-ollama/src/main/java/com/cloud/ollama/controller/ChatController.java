package com.cloud.ollama.controller;

import com.cloud.ollama.dto.ChatRequest;
import com.cloud.ollama.dto.ChatResponse;
import com.cloud.ollama.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/completion")
    public ChatResponse chatCompletion(@RequestBody ChatRequest request) {
        return chatService.chatCompletion(request);
    }

    @PostMapping(value = "/stream", produces = "text/event-stream")
    public Flux<String> chatStream(@RequestBody ChatRequest request) {
        return chatService.chatStream(request);
    }

    @GetMapping("/models")
    public String[] getAvailableModels() {
        return chatService.getAvailableModels();
    }
}