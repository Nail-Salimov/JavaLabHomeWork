package server.businesslogic.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import server.entity.queue.QueueModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
public class QueueRepositoryJpaImpl implements QueueRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addQueue(QueueModel queue) {
        entityManager.persist(queue);
    }

    @Override
    public boolean isExist(String queueName) {
        Long count = (Long)entityManager.createQuery("SELECT count(q) FROM QueueModel q WHERE q.queueName =:name")
                .setParameter("name", queueName).getSingleResult();
        return count.equals(1L);
    }

    @Override
    public Optional<QueueModel> findQueue(String queueName) {
        return Optional.of(entityManager.createQuery("SELECT q FROM QueueModel q WHERE q.queueName =:name", QueueModel.class)
                .setParameter("name", queueName).getSingleResult());
    }
}
