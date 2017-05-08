package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.MessageDTO;
import dido.auntaccount.dto.TokenDTO;
import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.entities.Message;
import dido.auntaccount.service.business.MessageService;
import dido.auntaccount.service.business.TokenService;
import dido.auntaccount.service.business.UserService;
import dido.auntaccount.service.filter.Secured;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/messages")
public class MessageController extends Controller {

    @Inject
    private MessageService service;

    @Inject
    TokenService tokenService;

    @Inject
    private UserService userService;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessage(@PathParam("param") Long messageId) {
        MessageDTO message = service.getMessage(messageId);
        return getResponseBuilder().entity(message).build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveMessage(MessageDTO message, @HeaderParam(TokenController.ACCESS_TOKEN) String token) throws Exception {
        TokenDTO foundToken = tokenService.getToken(token);
        UserDTO sender = userService.getUser(foundToken.getUserId());
        final Date creationDate = new Date(DateTime.now().getMillis());
        message.setCreationDate(creationDate).setSender(sender);
        MessageDTO savedMessage = service.saveMessage(message);
        return getResponseBuilder().entity(savedMessage).build();
    }

    @OPTIONS
    @Path("/")
    public Response saveMessagePreflight() throws Exception {
        return getResponseBuilder().build();
    }

}
