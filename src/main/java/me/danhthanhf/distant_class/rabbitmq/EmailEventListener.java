package me.danhthanhf.distant_class.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailEventListener {
    @RabbitListener(queues = "email-verification")
    public void handleEmailEvent(EmailVerificationEvent event) throws InterruptedException {
        System.out.println("Sending email to: " + event.email());
    }
}
