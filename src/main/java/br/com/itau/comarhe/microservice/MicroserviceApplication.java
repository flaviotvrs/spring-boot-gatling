package br.com.itau.comarhe.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.jms.annotation.EnableJms;

@EnableCaching
@EnableCircuitBreaker
//@EnableJms
@SpringBootApplication
public class MicroserviceApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MicroserviceApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceApplication.class, args);
	}
}