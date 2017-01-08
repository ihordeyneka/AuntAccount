package dido.auntaccount.service.rest;

import dido.auntaccount.entities.Message;
import dido.auntaccount.service.business.MessageService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/messages")
public class MessageController {

    @Inject
    private MessageService service;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessage(@PathParam("param") Long messageId) {
        Message message = service.getMessage(messageId);
        return Response.status(200).entity(message).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveMessage(Message message) throws Exception {
        Message savedMessage = service.saveMessage(message);
        return Response.status(200).entity(savedMessage).build();
    }
    
}
