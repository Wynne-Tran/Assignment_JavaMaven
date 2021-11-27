package com.example.assignment;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EntityScan(basePackages = {"com.example.assignment.*"})
@EnableJpaRepositories(basePackages = {"com.example.assignment.*"})
public class AssignmentApplication {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {

        SpringApplication.run(AssignmentApplication.class, args);
    }
    public static void restart() {
        System.out.println("Restarting spring boot app for loading image!!! ");
        try{
            Thread.sleep(3000L);
        }catch(Exception e) {}

        ApplicationArguments args = context.getBean(ApplicationArguments.class);
        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(AssignmentApplication.class, args.getSourceArgs());
        });
        thread.setDaemon(false);
        System.out.println("Restart done. ");
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {};
    }


}
