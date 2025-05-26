package com.gcode.personalFinance;

import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersonalFinanceApplication {
	private static Logger log = org.apache.logging.log4j.LogManager.getLogger(PersonalFinanceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(PersonalFinanceApplication.class, args);
		log.info("Servicio Iniciado");
	}

}
