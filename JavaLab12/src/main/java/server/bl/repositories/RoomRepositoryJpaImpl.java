package server.bl.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import server.entities.model.LinkModel;
import server.entities.model.RoomModel;
import server.entities.model.UserModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class RoomRepositoryJpaImpl implements RoomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addLink(LinkModel linkModel) {

    }

    @Override
    @Transactional
    public RoomModel addNewRoom(RoomModel roomModel) {
        Set<LinkModel> links = roomModel.getLinkModels();
        entityManager.persist(roomModel);

        for (LinkModel link : links) {
            entityManager.persist(link);
        }

        return roomModel;
    }

    @Override
    @Transactional
    public List<RoomModel> findAllRooms() {
        return entityManager.createQuery("SELECT r FROM RoomModel r", RoomModel.class).getResultList();
    }

    @Override
    @Transactional
    public List<LinkModel> findLinksByRoomId(RoomModel id) {
        return entityManager.createQuery("SELECT l FROM LinkModel l WHERE l.roomModel =:roomId", LinkModel.class)
                .setParameter("roomId", id).getResultList();
    }

    @Override
    @Transactional
    public Optional<RoomModel> findRoomById(Long id) {
        return Optional.of(entityManager.createQuery("SELECT r FROM RoomModel r WHERE r.id=:id", RoomModel.class)
                .setParameter("id", id).getSingleResult());
    }

    @Override
    @Transactional
    public boolean roomIsExist(Long id) {
        Long count = (Long) entityManager.createQuery("SELECT count(r) FROM RoomModel r WHERE r.id=:id")
                .setParameter("id", id).getSingleResult();

        return count.equals(1L);

    }

    @Override
    @Transactional
    public boolean linkIsExist(RoomModel room, UserModel userModel) {
        Long count = (Long) entityManager.createQuery("SELECT count(r) FROM LinkModel r WHERE r.roomModel=:room" +
                " and r.userModel=:userModel")
                .setParameter("room", room)
                .setParameter("userModel", userModel)
                .getSingleResult();


        return count.equals(1L);
    }

    @Override
    @Transactional
    public void addNewLink(LinkModel linkModel) {
        entityManager.persist(linkModel);
    }
}
