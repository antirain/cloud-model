package com.cloud.ollama.service.impl;

import com.cloud.ollama.dto.ChatRequest;
import com.cloud.ollama.dto.ChatResponse;
import com.cloud.ollama.service.ChatService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.output.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final OllamaChatModel ollamaChatModel;
    private final OllamaStreamingChatModel ollamaStreamingChatModel;

    @Override
    public ChatResponse chatCompletion(ChatRequest request) {
        long startTime = System.currentTimeMillis();
        
        try {
            String response = ollamaChatModel.generate(request.getMessage());
            long duration = System.currentTimeMillis() - startTime;
            
            ChatResponse chatResponse = new ChatResponse();
            chatResponse.setId(UUID.randomUUID().toString());
            chatResponse.setMessage(response);
            chatResponse.setModel(request.getModel());
            chatResponse.setTimestamp(LocalDateTime.now());
            chatResponse.setDurationMs(duration);
            
            log.info("Chat completion completed in {}ms", duration);
            return chatResponse;
            
        } catch (Exception e) {
            log.error("Chat completion failed", e);
            throw new RuntimeException("AI服务调用失败: " + e.getMessage());
        }
    }

    @Override
    public Flux<String> chatStream(ChatRequest request) {
        // 使用真正的LangChain4j流式API
        return Flux.create(sink -> {
            try {
                ollamaStreamingChatModel.generate(request.getMessage(), new StreamingResponseHandler<AiMessage>() {
                    @Override
                    public void onNext(String s) {
                        sink.next(s);
                    }

                    @Override
                    public void onComplete(Response<AiMessage> response) {
                        sink.complete();
                    }

                    @Override
                    public void onError(Throwable error) {
                        log.error("Stream chat failed", error);
                        sink.error(new RuntimeException("流式聊天失败: " + error.getMessage()));
                    }
                });
            } catch (Exception e) {
                log.error("Stream chat initialization failed", e);
                sink.error(new RuntimeException("流式聊天初始化失败: " + e.getMessage()));
            }
        });
    }

    @Override
    public String[] getAvailableModels() {
        // 这里可以集成Ollama的API来获取可用模型列表
        return new String[]{
            "deepseek-r1:8b",
            "llama2:13b", 
            "mistral:7b",
            "codellama:7b",
            "deepseek-coder:6.7b",
            "qwen:7b"
        };
    }
}