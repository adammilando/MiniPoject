package org.Camp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
@SpringBootApplication
public class CampProjectApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(CampProjectApplication.class,args);
    }
}