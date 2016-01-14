package nl.sogyo.academy.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistry {

    public static void main(String[] args) {
        if (args.length == 1) {
            System.setProperty("server.port", args[0]);
        } else {
            System.err.println("Missing argument: <port>. Please provide the port on which the service registry should run.");
            System.exit(-1);
        }

        SpringApplication.run(ServiceRegistry.class);
    }
}
