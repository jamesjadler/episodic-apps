package com.example.config;

import com.example.episodes.EpisodicProgressEvent;
import com.example.viewings.UpdateOrCreateViewing;
import com.example.viewings.UpdateOrCreateViewingFromEpisodicProgressEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class AmqpListener implements RabbitListenerConfigurer {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    UpdateOrCreateViewingFromEpisodicProgressEvent updateOrCreateViewingFromEpisodicProgressEvent;

    @RabbitListener(queues = "episodic-progress")
    @Transactional
    public void receiveMessage(final EpisodicProgressEvent message) throws Exception {
        System.out.println("************************************************");
        String messageString = mapper.writeValueAsString(message);
        System.out.println(messageString);
        System.out.println("************************************************");
        System.out.println("UPDATING OR CREATING VIEW FROM EPISODIC PROGRESS EVENT");
        updateOrCreateViewingFromEpisodicProgressEvent.process(message);
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(mapper);
        factory.setMessageConverter(converter);
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}
