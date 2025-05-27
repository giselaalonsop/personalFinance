package com.gcode.personalFinance;

import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class})
public class PersonalFinanceApplication {
	private static Logger log = org.apache.logging.log4j.LogManager.getLogger(PersonalFinanceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(PersonalFinanceApplication.class, args);
		log.info("Servicio Iniciado");
	}
	@Configuration
	public static class MyConfiguration {

		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
							.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
				}
			};
		}
	}
}
