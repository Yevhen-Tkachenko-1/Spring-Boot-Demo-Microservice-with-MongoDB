package com.yevhent.microservicedemo;

import com.yevhent.microservicedemo.loader.DemoCommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMicroservice implements CommandLineRunner {

	@Autowired
	private DemoCommandLineRunner demoCommandLineRunner;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMicroservice.class, args);
	}

	@Override
	public void run(String... args) {
		demoCommandLineRunner.initDatabase();
	}
}
