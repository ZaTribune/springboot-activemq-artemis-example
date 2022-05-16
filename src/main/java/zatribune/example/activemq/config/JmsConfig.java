package zatribune.example.activemq.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.Destination;

@Slf4j
@EnableJms
@Configuration
public class JmsConfig {

    @Value("${jms.queues.main-queue.name}")
    public String mainQueue;

    @Value("${jms.queues.chatting-queue.name:CHATTING_QUEUE}")
    public String chattingQueue;

    // this one is customized
    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter converter=new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    //Initialize queues
    @Bean(name="mainQueueDestination")
    public Destination mainQueueDestination() {
        return new ActiveMQQueue(mainQueue);
    }
    @Bean(name="chattingQueueDestination")
    public Destination chattingQueueDestination() {
        return new ActiveMQQueue(chattingQueue);
    }


}
