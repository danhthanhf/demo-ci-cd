package me.danhthanhf.distant_class.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue}")
    private String queueName;

    public void publishEmailEvent(EmailVerificationEvent event) {
        // convert event to JSON and send it to the
        rabbitTemplate.convertAndSend(RabbitMQConstants.USER_EXCHANGE, RabbitMQConstants.EMAIL_ROUTING_KEY, event);
    }
}
