package server.bl.services;

import org.springframework.web.multipart.MultipartFile;
import server.entities.document.Document;
import server.entities.document.DocumentDto;
import server.entities.user.UserDto;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface DocumentService {

    DocumentDto saveDocument(MultipartFile file, UserDto userDto);
    Optional<DocumentDto> findDocumentById(Long id);
    List<DocumentDto> findAllDocuments();
    File downloadDocument(String storageName);
}
