package br.com.ms.cardservice.config;

import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSqs
public class SqsConfig {
}
