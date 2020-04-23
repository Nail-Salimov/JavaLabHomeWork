package server.bl.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.bl.repositories.UserRepository;
import server.entities.dto.UserDto;
import server.entities.model.UserModel;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Optional<UserDto> saveUser(UserDto userDto) {

        if (!userRepository.findCount(userDto.getMail()).equals(1L)) {
            UserModel userModel = UserModel.builder()
                    .hashPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()))
                    .mail(userDto.getMail())
                    .name(userDto.getName())
                    .build();
            userRepository.save(userModel);

            return Optional.of(userDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean authorize(UserDto userDto) {
        Optional<UserModel> optionalUserModel = userRepository.findUserByMail(userDto.getMail());
        if (optionalUserModel.isPresent()) {
            UserModel userModel = optionalUserModel.get();
            return BCrypt.checkpw(userDto.getPassword(), userModel.getHashPassword());
        }
        return false;
    }

    @Override
    public Optional<UserDto> findUserByMail(String mail) {
        if (!userRepository.findCount(mail).equals(1L)) {
            return Optional.empty();
        }

        Optional<UserModel> optional = userRepository.findUserByMail(mail);
        if (!optional.isPresent()) {
            return Optional.empty();
        }

        UserModel userModel = optional.get();

        return Optional.of(UserDto.builder()
                .name(userModel.getName())
                .mail(userModel.getMail())
                .id(userModel.getId())
                .password(userModel.getHashPassword())
                .build());

    }

    @Override
    public Optional<UserDto> findUserById(Long id) {
        if (!userRepository.userIsExist(id)) {
            return Optional.empty();
        }

        Optional<UserModel> optional = userRepository.findUserById(id);
        if(!optional.isPresent()){
            return Optional.empty();
        }

        UserModel userModel = optional.get();

        return Optional.of(UserDto.builder()
                .name(userModel.getName())
                .mail(userModel.getMail())
                .id(userModel.getId())
                .password(userModel.getHashPassword())
                .build());
    }


}
