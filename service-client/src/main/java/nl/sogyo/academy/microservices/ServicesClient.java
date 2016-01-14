package nl.sogyo.academy.microservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ServicesClient {


    @Autowired
    private DiscoveryClient serviceRegistryClient;
    @Autowired
    private RestTemplate serviceInstanceClient;

    @RequestMapping("/service/{serviceName}")
    public List<ServiceInstance> showAllServiceInstances(@PathVariable("serviceName") String serviceName) {
        return serviceRegistryClient.getInstances(serviceName);
    }

    @RequestMapping("/service/{serviceName}/instance")
    public String showSingleServiceInstanceStatus(@PathVariable("serviceName") String serviceName) {
        return serviceInstanceClient.getForObject(String.format("http://%s/", serviceName), String.class);
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            System.setProperty("server.port", args[0]);
            System.setProperty("eureka.client.serviceUrl.defaultZone", String.format("http://%s/eureka/", args[1]));
        } else {
            System.err.println("Missing arguments: <service port> <service registry hostname>:<service registry port>.");
            System.exit(-1);
        }

        SpringApplication.run(ServicesClient.class);
    }
}
