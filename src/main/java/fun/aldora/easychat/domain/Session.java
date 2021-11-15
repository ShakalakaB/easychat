package fun.aldora.easychat.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Session {
    @Id
    private String id;

    private String chatId;

    private String senderId;

    private String recipientId;
}
