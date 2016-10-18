package dido.auntaccount.service;

import dido.auntaccount.dao.MessageDAO;
import dido.auntaccount.entities.Message;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/messages")
public class MessageService {

    @Inject
    private MessageDAO messageDAO;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessage(@PathParam("param") Long messageId) {
        Message message = messageDAO.find(messageId);
        return Response.status(200).entity(message).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveMessage(Message message) throws Exception {
        Message savedMessage = messageDAO.save(message);
        return Response.status(200).entity(savedMessage).build();
    }
    
}
