package com.swiftdeploy.swiftdeploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SwiftdeployApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiftdeployApplication.class, args);
	}

	@Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }

}
