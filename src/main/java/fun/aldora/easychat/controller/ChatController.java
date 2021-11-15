package fun.aldora.easychat.controller;

import fun.aldora.easychat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
//    private final
    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {

    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public Long countNewMessages(@PathVariable String senderId, @PathVariable String recipientId) {
        return null;
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public <T> T findChatMessages() {
        return null;
    }

    @GetMapping("/messages/{id}")
    public <T> T findMessage ( @PathVariable String id) {
        return null;
    }
}
