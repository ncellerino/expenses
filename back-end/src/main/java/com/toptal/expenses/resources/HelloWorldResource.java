package com.toptal.expenses.resources;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.toptal.expenses.model.Saying;

public class HelloWorldResource {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;
    
    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }
}
