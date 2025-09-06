package com.tanfed.basicInfo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class BasicInfoServiceApplication {

	@GetMapping("/")
	public String test() throws Exception {
		return "Service Deployed Successfully!";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BasicInfoServiceApplication.class, args);
	}

}
