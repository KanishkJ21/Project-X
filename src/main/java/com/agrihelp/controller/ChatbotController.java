package com.agrihelp.controller;

import com.agrihelp.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @GetMapping("/reply")
    public String getReply(@RequestParam String message) {
        // Calls the service to get a real-time response
        return chatbotService.generateReply(message);
    }
}
