package com.sarya.springai.controller;


import com.sarya.springai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gpt/structured-ouput")
public class StructuredOutputController {

    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    // output is automatically mapped to a POJO. Similarly we can make the llm return a list or map object by using Output confverter class
    @GetMapping("/chat-bean")
    public ResponseEntity<CountryCities> chatBean(@RequestParam("message")  String message){
        CountryCities countryCities =  chatClient.prompt().user(message).call().entity(CountryCities.class);

        return ResponseEntity.ok(countryCities);
    }

    // test using this prompt: http://localhost:8080/spring-ai/gpt/structured-ouput/chat-bean-list?message=give me the details of countries and cities in Europe
    /*
    [
    {
        "country": "France",
        "cities": [
            "Paris",
            "Marseille",
            "Lyon",
            "Toulouse",
            "Nice"
        ]
    },
    {
        "country": "Germany",
        "cities": [
            "Berlin",
            "Munich",
            "Frankfurt",
            "Hamburg",
            "Cologne"
        ]
    }]
    *
    * */
    @GetMapping("/chat-bean-list")
    public ResponseEntity<List<CountryCities>> chatBeanList(@RequestParam("message")  String message){
        List<CountryCities> countryCities =  chatClient.prompt().user(message).call()
                .entity(new ParameterizedTypeReference<List<CountryCities>>(){});

        return ResponseEntity.ok(countryCities);
    }





}
