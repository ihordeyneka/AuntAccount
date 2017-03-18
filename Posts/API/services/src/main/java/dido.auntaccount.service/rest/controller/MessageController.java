package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.MessageDTO;
import dido.auntaccount.entities.Message;
import dido.auntaccount.service.business.MessageService;
import dido.auntaccount.service.filter.Secured;

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
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessage(@PathParam("param") Long messageId) {
        MessageDTO message = service.getMessage(messageId);
        return Response.status(200).entity(message).build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveMessage(MessageDTO message) throws Exception {
        MessageDTO savedMessage = service.saveMessage(message);
        return Response.status(200).entity(savedMessage).build();
    }
    
}
