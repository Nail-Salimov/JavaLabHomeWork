package server.business_logic.services;

import server.entities.dto.UserDataDto;
import server.entities.model.UserDataModel;

import java.util.Optional;

public interface UserService {
    Optional<UserDataDto> findUserByMail(String mail);
    Optional<UserDataDto> findUserById(Long id);
    boolean saveUser(UserDataDto userDataDto);
    boolean confirmUser(String name, String mail, String token);
}
