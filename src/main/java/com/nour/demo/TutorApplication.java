package com.nour.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.nour.demo")
@EnableConfigurationProperties
@SpringBootApplication
public class TutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorApplication.class, args);
	}
}