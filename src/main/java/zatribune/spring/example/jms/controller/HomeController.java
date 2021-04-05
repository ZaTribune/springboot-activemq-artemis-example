package zatribune.spring.example.jms.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import zatribune.spring.example.jms.config.JmsConfig;

import java.util.List;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/queues")
    public String getMessageQueues(Model model) {
        log.info("Accessed queues");
        model.addAttribute("queues", List.of(JmsConfig.MAIN_QUEUE,JmsConfig.CHATTING_QUEUE));
        return "queues";
    }


}
