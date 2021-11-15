package fun.aldora.easychat.service.impl;

import fun.aldora.easychat.domain.ChatMessage;
import fun.aldora.easychat.domain.MessageStatus;
import fun.aldora.easychat.repository.ChatMessageRepository;
import fun.aldora.easychat.service.ChatMessageService;
import fun.aldora.easychat.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final SessionService sessionService;
    private final MongoOperations mongoOperations;

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.UNDELIVERED);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public Long countUndeliveredMessages(String senderId, String recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.UNDELIVERED);
    }

    @Override
    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        return null;
    }

    @Override
    public ChatMessage findById(String id) {
        return chatMessageRepository.findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                })
                .orElseThrow(() -> new RuntimeException("can't find message (" + id + ")"));
    }

    @Override
    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
        Query query = new Query(Criteria
                .where("senderId").is(senderId)
                .and("recipientId").is(recipientId)
        );
        Update update = Update.update("status", status);
        mongoOperations.updateMulti(query, update, ChatMessage.class);
    }
}
