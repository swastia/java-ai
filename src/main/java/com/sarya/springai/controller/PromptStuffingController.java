package com.sarya.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/systemPromptTemplate")
public class PromptStuffingController {


    private final ChatClient chatClient;

    public PromptStuffingController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Value("classpath:/templates/systemPromptTemplate.st")
    Resource systemPromptTemplate;
    @GetMapping("/leavePolicy")
    public String emailResponse(@RequestParam("customerMessage") String message) {
        return chatClient
                .prompt()
                .system(systemPromptTemplate)
                .user( message)
                .call().content();
    }

}
