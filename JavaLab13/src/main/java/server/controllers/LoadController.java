package server.controllers;

import com.zaxxer.hikari.util.IsolationLevel;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.bl.repositories.UserRepository;
import server.bl.services.DocumentService;
import server.bl.services.UserService;
import server.entities.document.DocumentDto;
import server.entities.user.UserModel;
import server.entities.user.UserDto;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Controller
public class LoadController {

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentService documentService;

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        UserDto userDto = (UserDto) session.getAttribute("user");
        Optional<UserDto> optional = userService.findUser(userDto.getMail(), userDto.getPassword());
        if(!optional.isPresent()){
            throw new IllegalArgumentException("user not found");
        }

        DocumentDto documentDto = documentService.saveDocument(file, optional.get());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/file/{fileId}")
    public void downloadFile(@PathVariable Long fileId, HttpServletRequest request, HttpServletResponse response) {
        if (fileId == null) {
           throw new IllegalArgumentException("file id is not valid");
        }

        Optional<DocumentDto> optional = documentService.findDocumentById(fileId);
        if(!optional.isPresent()){
            throw new IllegalArgumentException("file not found");
        }
        File file = documentService.downloadDocument(optional.get().getStorageName());
        Path path = file.toPath();
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(Files.probeContentType(path));
            response.addHeader("Content-Disposition", "inline; filename=" + optional.get().getOriginalName());
            Files.copy(path, response.getOutputStream());
            response.getOutputStream().flush();
        }catch (IOException e){
            throw new IllegalArgumentException(e);
        }

    }

}
