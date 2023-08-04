package com.wonderboy.wonderboy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@SpringBootApplication
public class WonderboyApplication {
	public static void main(String[] args) {
		SpringApplication.run(WonderboyApplication.class, args);
	}




}
