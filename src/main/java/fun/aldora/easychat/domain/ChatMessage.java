package fun.aldora.easychat.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class ChatMessage {
    @Id
    private String id;

    private String chatId;

    private String senderId;

    private String recipientId;

    private String senderName;

    private String recipientName;

    private String content;

    private LocalDateTime createdTime;

    private MessageStatus status;
}
