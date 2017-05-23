package com.escuelaing.arsw.msgbroker;

/**
 *
 * @author Carlos Alberto Ramirez Otero
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 *
 * @author FamiliaRamirez
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class WebSocketMSGBrokerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSocketMSGBrokerApplication.class, args);
    }
}
