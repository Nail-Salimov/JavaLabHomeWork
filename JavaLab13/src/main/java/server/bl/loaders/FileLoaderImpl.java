package server.bl.loaders;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import server.entities.document.Document;
import server.entities.user.UserModel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileLoaderImpl implements FileLoader{

    @Value("${db.filePath}")
    private String storagePath;

    @Override
    public Document uploadFile(MultipartFile file, UserModel user) {
        try {
            String originalName = file.getOriginalFilename();
            int index = originalName != null ? originalName.indexOf(".") : 0;
            String type = originalName != null ? originalName.substring(index, originalName.length()) : null;

            Document document = Document.builder()
                    .originalName(originalName)
                    .storageName(createStorageName(originalName))
                    .size(file.getSize())
                    .type(type)
                    .user(user)
                    .build();

            byte[] bytes = file.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(getPath() + document.getStorageName())));
            stream.write(bytes);
            stream.close();

            return document;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public File downloadFile(String storageName) {
        return new File(getPath() + storageName);
    }

    private String getPath() {
        return storagePath;
    }

    private static String createStorageName(String originalName) {
        String storageName = UUID.randomUUID().toString();
        String type = FilenameUtils.getExtension(originalName);
        return storageName + "." + type;
    }
}
