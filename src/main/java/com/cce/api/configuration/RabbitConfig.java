package com.cce.api.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${JAVA_CODE_SUBMISSION_EXCHANGE_NAME}")
    String javaCodeSubmissionExchange;

    @Value("${JAVA_CODE_SUBMISSION_QUEUE_NAME}")
    String javaCodeSubmissionQueue;

    @Value("${JAVA_CODE_SUBMISSION_ROUTING_KEY}")
    String javaCodeSubmissionRoutingKey;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory customContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(20);
        factory.setConcurrentConsumers(4);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(javaCodeSubmissionExchange);
    }

    @Bean
    public Queue queue() {
        return new Queue(javaCodeSubmissionQueue, true);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(javaCodeSubmissionRoutingKey);
    }
}
