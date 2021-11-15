package fun.aldora.easychat.service;

import fun.aldora.easychat.domain.ChatMessage;
import fun.aldora.easychat.domain.MessageStatus;

import java.util.List;

public interface ChatMessageService {
    ChatMessage save(ChatMessage chatMessage);

    Long countUndeliveredMessages(String senderId, String recipientId);

    List<ChatMessage> findChatMessages(String senderId, String recipientId);

    ChatMessage findById(String id);

    void updateStatuses(String senderId, String recipientId, MessageStatus status);
}
