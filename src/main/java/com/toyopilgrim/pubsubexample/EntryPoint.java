package com.toyopilgrim.pubsubexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.toyopilgrim.pubsubexample"})
public class EntryPoint {

	public static void main(String[] args) {
		SpringApplication.run(EntryPoint.class, args);
	}

}
