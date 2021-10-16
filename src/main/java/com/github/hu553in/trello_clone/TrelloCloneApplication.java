package com.github.hu553in.trello_clone;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// TODO: remove '(exclude = {SecurityAutoConfiguration.class})' when security is configured
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@OpenAPIDefinition(info = @Info(title = "Trello clone", version = "0.0.1-SNAPSHOT"))
public class TrelloCloneApplication {
    public static void main(final String[] args) {
        SpringApplication.run(TrelloCloneApplication.class, args);
    }
}
