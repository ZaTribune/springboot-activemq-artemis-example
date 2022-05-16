package zatribune.example.activemq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class SseService {

    Map<String, SseEmitter> clients1 = new HashMap<>();
    Map<String, SseEmitter> clients2 = new HashMap<>();

    public SseEmitter getInfiniteMessages(String queue, Authentication authentication) {
        SseEmitter emitter = new SseEmitter();
        Map<String, SseEmitter> map;
        if (queue.equals("MAIN_QUEUE"))
            map = clients1;
        else
            map = clients2;

        map.put(authentication.getName(), emitter);
        emitter.onCompletion(() -> map.remove(authentication.getName()));
        emitter.onError(ex -> {
            log.error("error: {}", ex.getMessage());
            map.remove(authentication.getName());
        });
        emitter.onTimeout(() -> map.remove(authentication.getName()));
        return emitter;
    }

    public void notifyListeners(String queue, String stringMessage) {
        Map<String, SseEmitter> map;
        if (queue.equals("MAIN_QUEUE"))
            map = clients1;
        else
            map = clients2;

        map.keySet().forEach(user -> {
            try {
                map.get(user).send(stringMessage);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("Sse message " + stringMessage + "not send", e);
            }
        });
    }
}
