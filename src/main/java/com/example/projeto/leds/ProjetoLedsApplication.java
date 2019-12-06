package com.example.projeto.leds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.projeto.leds.config.property.ProjetoLedsProperty;

@SpringBootApplication
@EnableConfigurationProperties(ProjetoLedsProperty.class)
public class ProjetoLedsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoLedsApplication.class, args);
	}

}
