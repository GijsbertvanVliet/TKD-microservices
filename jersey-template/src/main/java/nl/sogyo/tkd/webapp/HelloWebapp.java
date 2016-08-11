package nl.sogyo.tkd.webapp;

import nl.sogyo.tkd.service.HelloWorldService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("")
public class HelloWebapp {
	private static HelloWorldService helloWorldService = new HelloWorldService();
	
	@GET()
	public String hello() {
		return helloWorldService.hi();
	}
}
