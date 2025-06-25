package me.danhthanhf.distant_class.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {


    @Bean
    public Queue emailQueue() {
        return new Queue(RabbitMQConstants.EMAIL_QUEUE, false); // false means the queue is not durable
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(RabbitMQConstants.USER_EXCHANGE);
    }

    // ✅ Mô phỏng Default Exchange (DirectExchange tên "")
//    @Bean
//    public DirectExchange defaultExchange() {
//        return new DirectExchange(""); // chỉ để mô tả – không dùng thật
//    }
//
//    // ✅ Mô phỏng việc RabbitMQ tự bind queue với chính tên nó
//    @Bean
//    public Binding defaultBinding() {
//        return BindingBuilder.bind(emailQueue())
//                .to(defaultExchange())
//                .with(RabbitMQConstants.EMAIL_QUEUE); // routing key = chính tên queue
//    }


    @Bean
    public TopicExchange defaultExchange() {
        return new TopicExchange("");
    }

    @Bean
    public Binding defaultBinding() {
        return BindingBuilder.bind(emailQueue())
                .to(defaultExchange())
                .with(RabbitMQConstants.EMAIL_QUEUE);
    }

    // binding the queue to exchange with routing key "user.*"
    // every message with routing key starting with "user.<something>" when send to "user.exchange" will be send to emailQueue
    // user.* is different from user.#
    // user.* will match user.create, user.update, but not user.create.email
    // user.# will match user.create, user.update, user.create.email
    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue())
                .to(userExchange())
                .with("user.*");
    }


    // config convert message to avoid un-trusted package exception
    @Bean
    public org.springframework.amqp.support.converter.MessageConverter jsonMessageConverter() {
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setTrustedPackages("me.danhthanhf.distant_class.rabbitmq");

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(mapper);
        return converter;
    }
}
