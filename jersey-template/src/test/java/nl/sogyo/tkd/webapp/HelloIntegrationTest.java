package nl.sogyo.tkd.webapp;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HelloIntegrationTest {
  @Test
  public void testHello() throws Exception {
    Client client = JerseyClientBuilder.createClient();
    WebTarget target = client.target("http://localhost:8080/");
    String response = target.request().buildGet().invoke().readEntity(String.class);

    assertThat(response, is("Hi!"));
  }
}
