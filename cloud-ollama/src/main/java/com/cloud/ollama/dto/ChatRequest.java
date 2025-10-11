package com.cloud.ollama.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class ChatRequest {
    @NotBlank(message = "消息内容不能为空")
    private String message;
    
    private String model = "deepseek-r1:8b";
    
    private Double temperature = 0.7;
    
    private Integer maxTokens = 1000;
}