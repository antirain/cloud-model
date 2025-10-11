package com.cloud.ollama.service;

import com.cloud.ollama.dto.ChatRequest;
import com.cloud.ollama.dto.ChatResponse;
import reactor.core.publisher.Flux;

public interface ChatService {
    
    ChatResponse chatCompletion(ChatRequest request);
    
    Flux<String> chatStream(ChatRequest request);
    
    String[] getAvailableModels();
}