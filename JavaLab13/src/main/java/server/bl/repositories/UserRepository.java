package server.bl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.entities.user.UserModel;
import server.entities.userInformation.InformationDto;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query("SELECT u FROM UserModel u WHERE u.mail=:mail and u.password=:password")
    Optional<UserModel> findUserByMailAndPassword(@Param("mail") String mail, @Param("password") String password);

    @Query("SELECT new server.entities.userInformation.InformationDto(count(document)) FROM Document document where document.user.id=:userId")
    Optional<InformationDto> getInformation(@Param("userId") Long userId);
}
