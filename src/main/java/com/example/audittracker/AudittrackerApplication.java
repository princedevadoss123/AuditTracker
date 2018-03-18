package com.example.audittracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.audittracker" })
@EnableJpaRepositories("com.example.audittracker.dao")
@EntityScan("com.example.audittracker.domain")
public class AudittrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AudittrackerApplication.class, args);
	}
}
