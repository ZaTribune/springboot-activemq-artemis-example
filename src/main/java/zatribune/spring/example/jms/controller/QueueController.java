package zatribune.spring.example.jms.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import zatribune.spring.example.jms.config.JmsConfig;
import zatribune.spring.example.jms.db.entities.CustomMessage;
import zatribune.spring.example.jms.service.SseService;

import javax.jms.Message;
import javax.websocket.server.PathParam;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Controller
public class QueueController {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper mapper;
    private final SseService sseService;
    private Model messagingModel;
    private final Set<String> messages=new HashSet<>();

    @Autowired
    public QueueController(JmsTemplate jmsTemplate , ObjectMapper mapper , SseService sseService) {
        this.jmsTemplate = jmsTemplate;
        this.mapper=mapper;
        this.sseService=sseService;
    }

    @GetMapping("/queues/{queue}")
    public String showQueue(@PathVariable("queue") String queue, Model model){
        messagingModel=model;
        model.addAttribute("name",queue);
        model.addAttribute("message",new CustomMessage());
        model.addAttribute("messages",messages);
        return "showQueue";
    }

    @PostMapping("/queues/send")
    public @ResponseBody String sendMessage(@ModelAttribute CustomMessage message, @PathParam("queue") String queue){
        log.info(message.getContent());

//        MessageCreator messageCreator = session -> {
//            Message m = null;
//            try {
//                m = session.createTextMessage(mapper.writeValueAsString(message));
//                m.setStringProperty("_type", CustomMessage.class.getName());
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            assert false;
//            return m;
//        };
        jmsTemplate.convertAndSend(queue,message.getContent());
        return "redirect:/queues/"+queue;
    }

    @RequestMapping(path = "/sse/infinite", method = RequestMethod.GET)
    public SseEmitter getInfiniteMessages() {
        return sseService.getInfiniteMessages();
    }

    @JmsListener(destination= JmsConfig.CHATTING_QUEUE,concurrency = "100")
    public void receiveMessage(Message message) throws Exception {
        Thread.sleep(10); // simulated delay
        messages.add(message.getBody(String.class));
        if (messagingModel!=null) {
            messagingModel.addAttribute("messages", messages);
        }

        sseService.notifyListeners(message.getBody(String.class));

        //return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
        System.out.println("A message "+message.getBody(String.class)+" received on " + Thread.currentThread().getName());
    }
}
