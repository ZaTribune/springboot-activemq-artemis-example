package zatribune.spring.example.jms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Component
public class SseService {

    private final SseEmitter emitter;

    @Autowired
    public SseService() {
        emitter = new SseEmitter();
    }

    public SseEmitter getInfiniteMessages() {
        return emitter;
    }

    public void notifyListeners(String stringMessage) {
        try {
            emitter.send(stringMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Sse message " + stringMessage + "not send", e);
        }
    }
}
