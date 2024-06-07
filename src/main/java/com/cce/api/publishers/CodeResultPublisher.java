package com.cce.api.publishers;

import com.cce.api.dto.CodeSubmissionResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CodeResultPublisher {
    @Value("${RESULT_PUBLISH_EXCHANGE_NAME}")
    private String submitCodeExchange;

    @Value("${RESULT_PUBLISH_ROUTING_KEY}")
    private String submitCodeRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CodeResultPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(CodeSubmissionResponseDTO message){
        rabbitTemplate.convertAndSend(submitCodeExchange,submitCodeRoutingKey,message);
    }
}
