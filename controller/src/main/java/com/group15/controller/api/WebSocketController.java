package com.group15.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    int num = 0;

    @Scheduled(fixedRate = 1000)
    private void sendUpdate() {
        num++;
        broadcastUpdate(Integer.toString(num));
    }

    public void broadcastUpdate(@Payload String message) {
        System.out.println("message: " + message);
        template.convertAndSend("/topic/update", message);
    }
}
