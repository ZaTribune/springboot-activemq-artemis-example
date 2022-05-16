package zatribune.example.activemq.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class HomeController {

    @Value("${jms.queues.main-queue.name}")
    public String mainQueue;

    @Value("${jms.queues.chatting-queue.name:CHATTING_QUEUE}")
    public String chattingQueue;

    @GetMapping("/queues")
    public String getMessageQueues(Model model) {
        log.info("Accessed queues");
        model.addAttribute("queues", List.of(mainQueue, chattingQueue));
        return "queues";
    }


}
