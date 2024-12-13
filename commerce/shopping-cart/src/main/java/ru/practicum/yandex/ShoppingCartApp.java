package ru.practicum.yandex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ShoppingCartApp {
	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApp.class, args);
	}
}
