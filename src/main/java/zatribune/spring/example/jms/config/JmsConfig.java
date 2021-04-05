package zatribune.spring.example.jms.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.JMSException;

@Slf4j
@Configuration
public class JmsConfig {

    public static final String MAIN_QUEUE="MAIN_QUEUE";

    public static final String CHATTING_QUEUE="CHATTING_QUEUE";

    String BROKER_URL = "tcp://localhost:61616";
    String BROKER_USERNAME = "artemis";
    String BROKER_PASSWORD = "artemis";

    //theses are the configurations by default
    //configure the broker's connection
    @Bean
    public ActiveMQConnectionFactory  activeMQConnectionFactory() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setUser(BROKER_USERNAME);
        connectionFactory.setPassword(BROKER_PASSWORD);
        log.info("Broker Connection Factory created.");
        return connectionFactory;
    }

    //default behaviour
    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(activeMQConnectionFactory());
        return template;
    }

    //default behaviour
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws JMSException {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        //without editing this , a single instance of the application will listen
        /*
         * Specify concurrency limits via a "lower-upper" String, e.g. "5-10", or a simple
         * upper limit String, e.g. "10" (the lower limit will be 1 in this case).
         * This listener container will always hold on to the minimum number of consumers
         * and will slowly scale up to the maximum number of consumers in case of increasing load.
         */
        factory.setConcurrency("100");
        return factory;
    }

    // this one is customized
    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter converter=new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }


}
