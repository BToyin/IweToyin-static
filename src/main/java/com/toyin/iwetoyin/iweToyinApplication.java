package com.toyin.iwetoyin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class iweToyinApplication {

	public static void main(String[] args) {
		SpringApplication.run(iweToyinApplication.class, args);
	}

}
