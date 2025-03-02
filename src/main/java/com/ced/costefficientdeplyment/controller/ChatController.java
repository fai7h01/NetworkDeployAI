package com.ced.costefficientdeplyment.controller;

import com.ced.costefficientdeplyment.dto.ResponseWrapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/assistant")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> chat(@RequestParam(value = "q") String userMessage,
                                                @RequestParam(value = "address") String address,
                                                @RequestParam(value = "existing_pipeline_length") String existing,
                                                @RequestParam(value = "distance_between_existing_and_empty_pipeline") String distance) {

        String content = chatClient.prompt()
                .system(s -> s.params(Map.of(
                        "nonEmptyLength", 65.0,
                        "emptyLength", 25.0,
                        "connectionLength", 12.0
                        )))
                .user(userMessage)
                .advisors(a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, 1)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .call().content();
        return ResponseEntity.ok(ResponseWrapper.builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("Assistant response retrieved successfully.")
                .data(content)
                .build());
    }
}
