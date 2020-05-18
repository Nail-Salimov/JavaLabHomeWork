package server.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import server.bl.repositories.UserRepository;
import server.entities.document.Document;
import server.entities.document.DocumentDto;
import server.entities.user.UserDto;
import server.entities.user.UserModel;
import server.entities.userInformation.InformationDto;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isExist(String mail, String password) {
        return userRepository.findUserByMailAndPassword(mail, password).isPresent();
    }

    @Override
    public Optional<UserDto> findUser(String mail, String password) {
        Optional<UserModel> optional = userRepository.findUserByMailAndPassword(mail, password);
        return optional.map(this::castToUserDto);

    }

    @Override
    public Optional<UserDto> findUserById(Long id) {
       Optional<UserModel> optionalUserModel = userRepository.findById(id);
        return optionalUserModel.map(this::castToUserDto);
    }

    @Override
    public Optional<InformationDto> getInformation(Long userId) {
        return userRepository.getInformation(userId);
    }

    private UserDto castToUserDto(UserModel user) {
        return UserDto.builder()
                .id(user.getId())
                .mail(user.getMail())
                .password(user.getPassword())
                .mp4CountDocuments(castToSetDocumentDto(user.getMp4Documents()))
                .build();
    }

    private Set<DocumentDto> castToSetDocumentDto(Set<Document> documentSet){
        Set<DocumentDto> documentDtoSet = new HashSet<>();
        for (Document document : documentSet){
            documentDtoSet.add(castToDocumentDto(document));
        }
        return documentDtoSet;
    }

    private DocumentDto castToDocumentDto(Document document) {
        return DocumentDto.builder()
                .id(document.getId())
                .originalName(document.getOriginalName())
                .storageName(document.getStorageName())
                .size(document.getSize())
                .type(document.getType())
                .build();
    }
}
