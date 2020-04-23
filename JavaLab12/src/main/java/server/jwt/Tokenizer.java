package server.jwt;

import server.entities.dto.UserDto;
import server.entities.model.UserModel;

import java.util.Optional;

public interface Tokenizer {
    String getToken(String username, String email);
    boolean checkClient(String token, String username, String email);
    Optional<UserDto> getUser(String token);
}
