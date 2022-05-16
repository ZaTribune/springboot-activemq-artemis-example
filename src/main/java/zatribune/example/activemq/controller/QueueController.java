package zatribune.example.activemq.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import zatribune.example.activemq.db.entities.CustomMessage;
import zatribune.example.activemq.service.SseService;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class QueueController {

    private final JmsTemplate jmsTemplate;
    private final SseService sseService;

    @Autowired
    public QueueController(JmsTemplate jmsTemplate, SseService sseService) {
        this.jmsTemplate = jmsTemplate;
        this.sseService = sseService;
    }


    public List<String> receiveAllFromQueue(String destination) {
        return jmsTemplate.execute(session -> {
            try (final MessageConsumer consumer = session.createConsumer(session.createQueue(destination))) {
                List<String> messages = new ArrayList<>();
                Message message;
                while ((message = consumer.receiveNoWait()) != null) {
                    messages.add(message.getBody(String.class));
                }
                return messages;
            }
        }, true);
    }

    @Transactional
    @GetMapping("/queues/{queue}")
    public String showQueue(@PathVariable("queue") String queue, Model model) {
        model.addAttribute("queueName", queue);
        model.addAttribute("message", new CustomMessage());
        model.addAttribute("messages", receiveAllFromQueue(queue));
        return "showQueue";
    }

    @PostMapping("/queues/send")
    @ResponseBody
    public void sendMessage(@PathParam("queue") String queue, @ModelAttribute CustomMessage message) {
        log.info("message [{}] sent to queue [{}]",message.getContent(),queue);
        jmsTemplate.convertAndSend(queue, message.getContent());
        sseService.notifyListeners(queue,message.getContent());
    }

    @GetMapping("/sse/infinite/{queue}")
    public SseEmitter getInfiniteMessages(@PathVariable String queue, Authentication authentication) {
        log.info("user [{}] subscribed to queue [{}]",authentication.getName(),queue);
        return sseService.getInfiniteMessages(queue,authentication);
    }
}
