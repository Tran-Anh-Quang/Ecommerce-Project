package com.ecommerce.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"category","customer","product", "security", "setting"},
		exclude = {SecurityAutoConfiguration.class })
public class EcommerceFrontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceFrontEndApplication.class, args);
	}

}
