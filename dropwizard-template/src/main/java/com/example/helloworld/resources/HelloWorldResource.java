package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.api.Saying;
import com.example.helloworld.core.Template;
import com.example.helloworld.customer.AddNewCustomer;
import com.example.helloworld.customer.CustomerObject;
import com.example.helloworld.customer.ReplaceCustomer;
import com.example.helloworld.customer.RetrieveCustomer;
import io.dropwizard.jersey.caching.CacheControl;
import io.dropwizard.jersey.params.DateTimeParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Path("/customerService")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldResource.class);

    private final Template template;
    private final AtomicLong counter;

    public HelloWorldResource(Template template) {
        this.template = template;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed(name = "get-requests")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return new Saying(counter.incrementAndGet(), template.render(name));
    }

    @GET
    @Path("/getCustomer")
    @Timed(name = "getcustomer")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public CustomerObject getCustomer(@QueryParam("id") Optional<String> customerId) {
        return RetrieveCustomer.getCustomerByCustomerId(customerId.get());
    }

    @POST
    @Path("/addCustomer")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public void addCustomer(CustomerObject customer) {
        AddNewCustomer.addCustomer(customer);
    }

    @POST
    @Path("/updateCustomer")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public void updateCustomer(CustomerObject customer, @QueryParam("id") Optional<String> customerId) {
        int locationOfCustomer = RetrieveCustomer.getLocationOfCustomerWithId(customerId.get());
        ReplaceCustomer.replaceCustomer(locationOfCustomer, customer);
    }

    @POST
    public void receiveHello(@Valid Saying saying) {
        LOGGER.info("Received a saying: {}", saying);
    }

    @GET
    @Path("/date")
    @Produces(MediaType.TEXT_PLAIN)
    public String receiveDate(@QueryParam("date") Optional<DateTimeParam> dateTimeParam) {
        if (dateTimeParam.isPresent()) {
            final DateTimeParam actualDateTimeParam = dateTimeParam.get();
            LOGGER.info("Received a date: {}", actualDateTimeParam);
            return actualDateTimeParam.get().toString();
        } else {
            LOGGER.warn("No received date");
            return null;
        }
    }
}
