package com.spring.challenge_2;

import com.spring.challenge_2.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.example.something", "com.example.application"})

public class Challenge2Application implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Challenge2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		menu Menu = new menu(repository);
		Menu.mostrar();
}}
