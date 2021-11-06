package com.example.assignment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EntityScan(basePackages = {"com.example.assignment.*"})
@EnableJpaRepositories(basePackages = {"com.example.assignment.*"})
public class AssignmentApplication {

    public static void main(String[] args) {

        SpringApplication.run(AssignmentApplication.class, args);
    }

}
