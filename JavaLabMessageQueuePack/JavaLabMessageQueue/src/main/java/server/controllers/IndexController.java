package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import server.businesslogic.dispatchers.Converter;
import server.businesslogic.services.TaskService;
import server.entity.queue.QueueDto;

@Controller
public class IndexController {

    @Autowired
    private Converter dispatcher;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getPage(Model model){

        model.addAttribute("response", "void");
        QueueDto queueDto = QueueDto.builder()
                .queueName("document")
                .build();

        taskService.addQueue(queueDto);

        return "index";
    }
}
