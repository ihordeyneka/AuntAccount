package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.*;
import dido.auntaccount.service.business.MessageService;
import dido.auntaccount.service.business.OfferService;
import dido.auntaccount.service.business.PostService;
import dido.auntaccount.service.business.UserService;
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
import java.util.List;

import static dido.auntaccount.service.filter.AuthenticationFilter.LOGGED_IN_USER;

@Path("/offers")
public class OfferController extends Controller {

    @Inject
    OfferService service;

    @Inject
    MessageService messageService;

    @Inject
    UserService userService;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffer(@PathParam("param") Long offerId) {
        OfferDTO offer = service.getOffer(offerId);
        return getResponseBuilder().entity(offer).build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveOffer(OfferMessageDTO offer, @HeaderParam(LOGGED_IN_USER) String loggedInUser) throws Exception {
        OfferDTO savedOffer = service.saveOffer(offer, Long.valueOf(loggedInUser));
        return getResponseBuilder().entity(savedOffer).build();
    }

    @POST
    @Path("/upload")
    @Secured
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveOfferPicture(@FormDataParam("file_data") InputStream uploadedInputStream, @FormDataParam("postId") Long postId,
                                     @FormDataParam("sellerId") Long sellerId, @HeaderParam(LOGGED_IN_USER) String loggedInUserId) throws Exception {
        OfferDTO savedOffer = service.saveOfferPicture(uploadedInputStream, postId, sellerId, Long.valueOf(loggedInUserId));
        return getResponseBuilder().entity(savedOffer).build();
    }

    @GET
    @Path("/{param}/seller")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferSeller(@PathParam("param") Long offerId) {
        SellerDTO seller = service.getOfferSeller(offerId);
        return getResponseBuilder().entity(seller).build();
    }

    @GET
    @Path("/{param}/messages")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferMessages(@PathParam("param") Long offerId) {
        List<MessageDTO> messages = service.getOfferMessages(offerId);
        final PostDTO post = service.getOfferPost(offerId);
        final UserDTO user = userService.getUser(post.getUserId());
        final PostDetailsDTO postDetails = new PostDetailsDTO(post, user);
        return getResponseBuilder().entity(new OfferMessagesDTO(postDetails, messages)).build();
    }

    @OPTIONS
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferPreflight(@PathParam("param") Long offerId) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveOfferPreflight(OfferDTO offer) throws Exception {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}/seller")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferSellerPreflight(@PathParam("param") Long offerId) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}/messages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferMessagesPreflight(@PathParam("param") Long offerId) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/upload")
    public Response saveOfferPicturePreflight() {
        return getResponseBuilder().build();
    }

}
