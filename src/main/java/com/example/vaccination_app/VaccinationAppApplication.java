package com.example.vaccination_app;

import jdk.internal.ref.CleanerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaccinationAppApplication {

	private static CleanerImpl SpringApplication;

	public static void main(String[] args) {
		SpringApplication.run(VaccinationAppApplication.class, args);
	}

}
