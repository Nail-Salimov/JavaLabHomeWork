package server.addition.chat.bl.repository;

import org.springframework.stereotype.Component;
import server.addition.chat.entity.model.ChatModel;
import server.addition.chat.entity.model.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ChatRepositoryImpl implements ChatRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addChat(ChatModel chatModel) {
        Set<Member> members = chatModel.getMembers();
        entityManager.persist(chatModel);

        for (Member m : members){
            entityManager.persist(m);
        }

    }

    @Override
    @Transactional
    public Optional<ChatModel> findChat(Long id) {
        ChatModel chatModel = entityManager.createQuery("SELECT c FROM ChatModel c WHERE c.id =:id", ChatModel.class)
                .setParameter("id", id).getSingleResult();

        if(chatModel != null){
            return Optional.of(chatModel);
        }
        return Optional.empty();
    }

    @Override
    public List<ChatModel> findAllChatsByUserId(Long id) {
      List<ChatModel> list = new LinkedList<>();

      List<Member> memberList = entityManager.createQuery("SELECT m FROM Member m WHERE m.memberId = :id", Member.class)
              .setParameter("id", id).getResultList();

      for (Member m : memberList){
          list.add(m.getChatModel());
      }

      return list;
    }

}
