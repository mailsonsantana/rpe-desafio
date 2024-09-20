package br.com.ms.carrierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.com.ms.carrierservice")
public class CarrierServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarrierServiceApplication.class, args);
	}

}
