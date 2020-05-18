package server.bl.services;

import server.entities.user.UserDto;
import server.entities.userInformation.InformationDto;

import java.util.Optional;

public interface UserService {
    boolean isExist(String mail, String password);
    Optional<UserDto> findUser(String mail, String password);
    Optional<UserDto> findUserById(Long id);
    Optional<InformationDto> getInformation(Long userId);
}
