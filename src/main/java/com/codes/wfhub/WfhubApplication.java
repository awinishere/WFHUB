package com.codes.wfhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WfhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(WfhubApplication.class, args);
	}

}
