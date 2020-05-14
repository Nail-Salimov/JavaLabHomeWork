package server.addition.chat.bl.repository;

import org.springframework.stereotype.Component;
import server.addition.chat.entity.model.MessageModel;
import server.addition.chat.entity.model.NotifyModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.ListIterator;


@Component
public class MessageRepositoryImpl implements MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addMessage(MessageModel message) {
        List<NotifyModel> paths = message.getPaths();
        entityManager.persist(message);

        for (NotifyModel p : paths) {
            //System.out.println("Path state " + p.getState());
            entityManager.persist(p);
        }
    }

    @Override
    @Transactional
    public void updatePath(NotifyModel path) {
        entityManager.merge(path);
    }

    @Override
    @Transactional
    public List<NotifyModel> findNewMessageWithReading(Long roomId, Long userId) {
        List<NotifyModel> paths = entityManager.createQuery("SELECT p FROM NotifyModel p WHERE p.receiverId = :id and p.state = false", NotifyModel.class)
                .setParameter("id", userId).getResultList();


        ListIterator<NotifyModel> iterator = paths.listIterator();
        while (iterator.hasNext()) {
            NotifyModel model = iterator.next();
            if (model.getMessage().getChatModel().getId().equals(roomId)) {
                model.setState(true);
                updatePath(model);
            } else {
                iterator.remove();
            }
        }

        return paths;
    }

    @Override
    @Transactional
    public List<NotifyModel> findNewMessageWithoutReading(Long roomId, Long userId) {
        List<NotifyModel> paths = entityManager.createQuery("SELECT p FROM NotifyModel p WHERE p.receiverId = :id and p.state = false", NotifyModel.class)
                .setParameter("id", userId).getResultList();


        paths.removeIf(model -> !model.getMessage().getChatModel().getId().equals(roomId));
        return paths;
    }
}
