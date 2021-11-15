package fun.aldora.easychat.service.impl;

import fun.aldora.easychat.domain.Session;
import fun.aldora.easychat.repository.SessionRepository;
import fun.aldora.easychat.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist) {
        return sessionRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(Session::getChatId)
                .or(() -> {
                    if(!createIfNotExist) {
                        return  Optional.empty();
                    }
                    var chatId =
                            String.format("%s_%s", senderId, recipientId);

                    Session senderRecipient = Session
                            .builder()
                            .chatId(chatId)
                            .senderId(senderId)
                            .recipientId(recipientId)
                            .build();

                    Session recipientSender = Session
                            .builder()
                            .chatId(chatId)
                            .senderId(recipientId)
                            .recipientId(senderId)
                            .build();
                    sessionRepository.save(senderRecipient);
                    sessionRepository.save(recipientSender);

                    return Optional.of(chatId);
                });
    }
}
