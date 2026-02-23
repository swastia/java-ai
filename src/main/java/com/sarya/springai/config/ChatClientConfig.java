package com.sarya.springai.config;


import com.sarya.springai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
//       ChatOptions chatOptions =  ChatOptions.builder().model(String.valueOf(OpenAiApi.ChatModel.GPT_4_1_MINI))
//                .temperature(0.8).build();
//


        return chatClientBuilder
//                .defaultOptions(chatOptions)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
                .defaultSystem("""
                         You are an internal IT helpdesk assistant. Your role is to assist\s
                                                       employees with IT-related issues such as resetting passwords,\s
                                                       unlocking accounts, and answering questions related to IT policies.
                                                       If a user requests help with anything outside of these\s
                                                       responsibilities, respond politely and inform them that you are\s
                                                       only able to assist with IT support tasks within your defined scope.
                        """)
                .build();
    }
}
