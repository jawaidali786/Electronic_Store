package com.ElectronicStoreSpringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {

	public static void main(String[] args){
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("abcd"));

	}
}
