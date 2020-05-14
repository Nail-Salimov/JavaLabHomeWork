package server.shop.bl.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import server.shop.entities.model.OrderModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class OrderRepositoryJpaImpl implements OrderRepositoryJpa {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<OrderModel> find(Long orderId) {
        return Optional.of(entityManager.createQuery("SELECT p FROM OrderModel p WHERE p.id=:id", OrderModel.class)
                .setParameter("id", orderId).getSingleResult());
    }

    @Override
    public List<OrderModel> findAll() {
        return null;
    }

    @Override
    @Transactional
    public OrderModel save(OrderModel entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    @Transactional
    public List<OrderModel> findAllBySellerId(Long id) {
        return entityManager.createQuery("SELECT p FROM OrderModel p WHERE p.sellerId=:id", OrderModel.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<OrderModel> findNotAcceptedOrderByBuyerId(Long id) {
        return entityManager.createQuery("SELECT p FROM OrderModel p WHERE p.buyerId=:id AND p.orderState <> 'ACCEPTED'"
                , OrderModel.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    @Transactional
    public void update(OrderModel orderModel) {
        entityManager.merge(orderModel);
    }
}
