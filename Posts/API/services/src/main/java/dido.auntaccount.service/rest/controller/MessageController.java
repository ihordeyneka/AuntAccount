package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.*;
import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.service.business.*;
import dido.auntaccount.service.filter.AuthenticationFilter;
import dido.auntaccount.service.filter.Secured;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static dido.auntaccount.service.filter.AuthenticationFilter.LOGGED_IN_USER;

@Path("/messages")
public class MessageController extends Controller {

    @Inject
    private MessageService service;

    @Inject
    TokenService tokenService;

    @Inject
    OfferService offerService;

    @Inject
    PostService postService;

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
    public Response saveMessage(MessageDTO message, @HeaderParam(LOGGED_IN_USER) String loggedInUserId) throws Exception {
        UserDTO sender = userService.getUser(Long.valueOf(loggedInUserId));
        final Date creationDate = new Date(DateTime.now().getMillis());
        message.setCreationDate(creationDate).setSender(sender);
        MessageDTO savedMessage = service.saveMessage(message);
        return getResponseBuilder().entity(savedMessage).build();
    }

    @POST
    @Path("/upload")
    @Secured
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveMessagePicture(@FormDataParam("file_data") InputStream uploadedInputStream, @FormDataParam("offerId") Long offerId,
                                       @HeaderParam(LOGGED_IN_USER) String loggedInUserId) throws Exception {
        final Long user = Long.valueOf(loggedInUserId);
        final MessageDTO savedMessage = service.saveMessage(uploadedInputStream, offerId, user);
        return getResponseBuilder().entity(savedMessage).build();
    }

    @OPTIONS
    @Path("/")
    public Response saveMessagePreflight() throws Exception {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessagePreflight(@PathParam("param") Long messageId) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/upload")
    public Response saveMessagePicturePreflight() throws Exception {
        return getResponseBuilder().build();
    }


}
