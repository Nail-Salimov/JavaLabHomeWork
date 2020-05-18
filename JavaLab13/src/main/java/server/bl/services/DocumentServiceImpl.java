package server.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import server.bl.loaders.FileLoader;
import server.bl.repositories.DocumentRepository;
import server.bl.repositories.UserRepository;
import server.entities.document.Document;
import server.entities.document.DocumentDto;
import server.entities.user.UserModel;
import server.entities.user.UserDto;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FileLoader fileLoader;

    @Override
    public DocumentDto saveDocument(MultipartFile file, UserDto userDto) {
        Optional<UserModel> user = userRepository.findUserByMailAndPassword(userDto.getMail(), userDto.getPassword());
        if(!user.isPresent()){
            throw new IllegalArgumentException("user not found");
        }

        Document document = fileLoader.uploadFile(file, user.get());
        document = documentRepository.save(document);
        return castToDocumentDto(document, user.get());
    }

    @Override
    public Optional<DocumentDto> findDocumentById(Long id) {
        Optional<Document> optionalDocument = documentRepository.findById(id);
        return optionalDocument.map(this::castToDocumentDto);

    }

    @Override
    public List<DocumentDto> findAllDocuments() {
        List<Document> documentList = documentRepository.findAll();
        List<DocumentDto> documentDtoList = new LinkedList<>();
        for (Document document : documentList) {
            documentDtoList.add(castToDocumentDto(document));
        }
        return documentDtoList;
    }

    @Override
    public File downloadDocument(String storageName) {
        return fileLoader.downloadFile(storageName);
    }

    private DocumentDto castToDocumentDto(Document document, UserModel user) {
        return DocumentDto.builder()
                .id(document.getId())
                .originalName(document.getOriginalName())
                .storageName(document.getStorageName())
                .size(document.getSize())
                .type(document.getType())
                .user(castToUserDto(user))
                .build();
    }

    private DocumentDto castToDocumentDto(Document document) {
        return DocumentDto.builder()
                .id(document.getId())
                .originalName(document.getOriginalName())
                .storageName(document.getStorageName())
                .size(document.getSize())
                .user(castToUserDto(document.getUser()))
                .type(document.getType())
                .build();
    }

    private UserDto castToUserDto(UserModel user) {
        return UserDto.builder()
                .id(user.getId())
                .mail(user.getMail())
                .build();
    }
}
