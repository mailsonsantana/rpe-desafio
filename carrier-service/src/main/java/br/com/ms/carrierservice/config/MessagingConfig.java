package br.com.ms.carrierservice.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import com.amazonaws.services.sqs.AmazonSQS;

@Configuration
public class MessagingConfig {

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQS amazonSQS, MessageConverter messageConverter) {
        return new QueueMessagingTemplate((AmazonSQSAsync) amazonSQS);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new StringMessageConverter();
    }
}

