package fun.aldora.easychat.repository;

import fun.aldora.easychat.domain.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SessionRepository extends MongoRepository<Session, String> {
    Optional<Session> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
