package nl.sogyo.academy.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class SimpleService {

    @RequestMapping("/")
    public String getStatus() {
        return "I'm running fine on port " + System.getProperty("server.port");
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            System.setProperty("server.port", args[0]);
            System.setProperty("eureka.client.serviceUrl.defaultZone", String.format("http://%s/eureka/", args[1]));
        } else {
            System.err.println("Missing arguments: <service port> <service registry hostname>:<service registry port>.");
            System.exit(-1);
        }

        SpringApplication.run(SimpleService.class);
    }
}
