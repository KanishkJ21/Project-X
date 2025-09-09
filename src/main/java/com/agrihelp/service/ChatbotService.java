package com.agrihelp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ChatbotService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://api.example-chatbot.com/getReply"; // Replace with real API endpoint
    private final String API_KEY = "YOUR_API_KEY_HERE"; // If required

    public String generateReply(String userMessage) {
        // Build request URL with query params
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("message", userMessage)
                .queryParam("apiKey", API_KEY)
                .toUriString();

        try {
            // Call external chatbot API
            String response = restTemplate.getForObject(url, String.class);
            return response != null ? response : "Sorry, I couldn't get a response.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error contacting chatbot API.";
        }
    }
}

