package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import server.bl.services.DocumentService;
import server.entities.document.Document;
import server.entities.document.DocumentDto;

import java.util.List;

@Controller
public class FilesController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/files")
    public String getPage(Model model){
        List<DocumentDto> documentDtoList = documentService.findAllDocuments();
        model.addAttribute("documents", documentDtoList);
        return "files";
    }
}
