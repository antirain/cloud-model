package com.cloud.ollama.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatResponse {
    private String id;
    private String message;
    private String model;
    private Long tokensUsed;
    private LocalDateTime timestamp;
    private Long durationMs;
}