package server.bl.services;


import server.entities.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> saveUser(UserDto userDto);
    boolean authorize(UserDto userDto);
    Optional<UserDto> findUserByMail(String mail);
    Optional<UserDto> findUserById(Long id);

}
