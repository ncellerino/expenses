package com.toptal.expenses;

import com.toptal.expenses.resources.HelloWorldResource;

import health.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) throws Exception {
        //adding a resource
        final HelloWorldResource resource = new HelloWorldResource(configuration.getTemplate(),
                configuration.getDefaultName());
        environment.jersey().register(resource);
        //adding a health check
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // nothing to do yet
    }
}
