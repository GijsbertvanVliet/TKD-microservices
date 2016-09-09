package com.example.helloworld;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.example.helloworld.cli.RenderCommand;
import com.example.helloworld.core.Template;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.Arrays;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

  public HelloWorldApplication(ConsulClient client) {
    this.client = client;
  }

  ConsulClient client;

  public static void main(String[] args) throws Exception {
    ConsulClient client = new ConsulClient("10.10.4.191:8500");
    new HelloWorldApplication(client).run(args);
  }

  @Override
  public String getName() {
    return "hello-world";
  }

  @Override
  public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
    String serviceid = "customerService";
    NewService newService = new NewService();
    newService.setId(serviceid);
    newService.setName(serviceid);
    newService.setPort(8080);
    newService.setAddress("10.0.2.1");
    client.agentServiceRegister(newService);
    // Enable variable substitution with environment variables
    bootstrap.setConfigurationSourceProvider(
        new SubstitutingSourceProvider(
            bootstrap.getConfigurationSourceProvider(),
            new EnvironmentVariableSubstitutor(false)
        )
    );

    bootstrap.addCommand(new RenderCommand());
    bootstrap.addBundle(new AssetsBundle());
  }

  @Override
  public void run(HelloWorldConfiguration configuration, Environment environment) {
    final Template template = configuration.buildTemplate();

    environment.healthChecks().register("template", new TemplateHealthCheck(template));
    environment.jersey().register(new HelloWorldResource(template));
  }
}
