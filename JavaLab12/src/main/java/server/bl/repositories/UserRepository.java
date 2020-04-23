package server.bl.repositories;


import server.entities.model.UserModel;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Long, UserModel> {

    Optional<UserModel> findUserByMail(String mail);
    Optional<UserModel> findUserById(Long id);
    boolean userIsExist(Long id);
    Long findCount(String mail);

}
