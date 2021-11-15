package fun.aldora.easychat.service;

import java.util.Optional;

public interface SessionService {
    Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist);
}
