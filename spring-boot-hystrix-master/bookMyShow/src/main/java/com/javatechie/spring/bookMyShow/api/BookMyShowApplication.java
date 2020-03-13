package com.javatechie.spring.bookMyShow.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@RestController
@EnableHystrix
@EnableHystrixDashboard
public class BookMyShowApplication {

	@Autowired
	private RestTemplate template;

	@HystrixCommand(groupKey = "java techie", commandKey = "java techie", fallbackMethod = "bookMyShowFallBack")
	@GetMapping("/bookNow")
	public String bookShow() {
		String emailServiceResponse = template.getForObject("http://localhost:8181/emailService/send", String.class);
		String paymentServiceResponse = template.getForObject("http://localhost:8282/paymentService/pay", String.class);
		return emailServiceResponse + "\n" + paymentServiceResponse;
	}

	@GetMapping("/bookNowWitoutHystrix")
	public String bookShowWitoutHystrix() {
		String emailServiceResponse = template.getForObject("http://localhost:8181/emailService/send", String.class);
		String paymentServiceResponse = template.getForObject("http://localhost:8282/paymentService/pay", String.class);
		return emailServiceResponse + "\n" + paymentServiceResponse;
	}

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

	public String bookMyShowFallBack() {
		return "service gateway failed...";
	}

	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
}
