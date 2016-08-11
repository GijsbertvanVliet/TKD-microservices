package com.example.helloworld;

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

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
  public static void main(String[] args) throws Exception {
    new HelloWorldApplication().run(args);
  }

  @Override
  public String getName() {
    return "hello-world";
  }

  @Override
  public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
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
