package com.project.socializingApp;

import com.project.socializingApp.configuration.SwagConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SocializingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocializingAppApplication.class, args);
	}

}
