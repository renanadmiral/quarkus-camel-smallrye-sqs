package com.renan;

import com.fasterxml.jackson.databind.JsonNode;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class Controller {

    @Inject
    Producer producer;

    @POST
    @Produces
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUser(final JsonNode request) {
        producer.produce(request);
        return Response.status(Response.Status.CREATED).build();
    }
}
