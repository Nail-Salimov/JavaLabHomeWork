package server.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.bl.repositories.UserRepository;
import server.entities.model.UserDataModel;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Optional<UserDataModel> optionalUser =  userRepository.findByMail(mail);
        if(optionalUser.isPresent()){
            UserDataModel user = optionalUser.get();
            return new UserDetailImpl(user);
        }
        throw  new UsernameNotFoundException("user not found");
    }
}
