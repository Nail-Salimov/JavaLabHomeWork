package server.bl.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import server.entities.model.UserModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryJpaImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<UserModel> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<UserModel> findAll() {
        return null;
    }

    @Override
    @Transactional
    public UserModel save(UserModel entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    @Transactional
    public Optional<UserModel> findUserByMail(String mail) {
        UserModel userModel = entityManager.createQuery("SELECT u FROM UserModel u WHERE u.mail = :mail", UserModel.class)
                .setParameter("mail", mail).getSingleResult();

        return Optional.of(userModel);

    }

    @Override
    public Optional<UserModel> findUserById(Long id) {
        return Optional.of(entityManager.createQuery("SELECT u FROM UserModel u WHERE u.id=:id", UserModel.class)
                .setParameter("id", id).getSingleResult());
    }

    @Override
    public boolean userIsExist(Long id) {
        Long count = (Long) entityManager.createQuery("SELECT count(u) FROM UserModel u WHERE u.id=:id")
                .setParameter("id", id).getSingleResult();
        return count.equals(1L);
    }

    @Override
    @Transactional
    public Long findCount(String mail) {
        return (Long) entityManager.createQuery("SELECT count(u) FROM UserModel u WHERE u.mail = :mail")
                .setParameter("mail", mail).getSingleResult();
    }
}
