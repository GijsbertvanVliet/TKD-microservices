package com.example.helloworld;

import com.example.helloworld.api.Saying;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest {

  private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-example.yml");

  @ClassRule
  public static final DropwizardAppRule<HelloWorldConfiguration> RULE = new DropwizardAppRule<>(
      HelloWorldApplication.class, CONFIG_PATH);

  private Client client;

  @Before
  public void setUp() throws Exception {
    client = ClientBuilder.newClient();
  }

  @After
  public void tearDown() throws Exception {
    client.close();
  }

//  @Test
//  public void testHelloWorld() throws Exception {
//    final Optional<String> name = Optional.of("Dr. IntegrationTest");
//    final Saying saying = client.target("http://localhost:" + RULE.getLocalPort() + "/hello-world")
//        .queryParam("name", name.get())
//        .request()
//        .get(Saying.class);
//    assertThat(saying.getContent()).isEqualTo(RULE.getConfiguration().buildTemplate().render(name));
//  }
}
