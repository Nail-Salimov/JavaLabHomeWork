package server.bl.loaders;

import org.springframework.web.multipart.MultipartFile;
import server.entities.document.Document;
import server.entities.user.UserModel;

import java.io.File;

public interface FileLoader {

    Document uploadFile(MultipartFile file, UserModel user);
    File downloadFile(String storageName);
}
