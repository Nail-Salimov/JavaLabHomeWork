package server.bl.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import server.entities.model.MessageModel;
import server.entities.model.UserModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class MessageRepositoryJpaImpl implements MessageRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<MessageModel> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<MessageModel> findAll() {
        return null;
    }

    @Override
    @Transactional
    public MessageModel save(MessageModel entity) {
        UserModel userModel = entity.getUserModel();
        entityManager.persist(entity);
        entityManager.persist(userModel);
        return entity;

    }

    @Override
    public void delete(Long aLong) {

    }
}
