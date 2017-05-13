package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.MessageDTO;
import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.dto.SellerDTO;
import dido.auntaccount.service.business.OfferService;
import dido.auntaccount.service.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/offers")
public class OfferController extends Controller {

    @Inject
    OfferService service;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffer(@PathParam("param") Long offerId) {
        OfferDTO offer = service.getOffer(offerId);
        return getResponseBuilder().status(200).entity(offer).build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveOffer(OfferDTO offer) throws Exception {
        OfferDTO savedOffer = service.saveOffer(offer);
        return getResponseBuilder().status(200).entity(savedOffer).build();
    }

    @GET
    @Path("/{param}/seller")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferSeller(@PathParam("param") Long offerId) {
        SellerDTO seller = service.getOfferSeller(offerId);
        return getResponseBuilder().status(200).entity(seller).build();
    }

    @GET
    @Path("/{param}/messages")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferMessages(@PathParam("param") Long offerId) {
        List<MessageDTO> messages = service.getOfferMessages(offerId);
        return getResponseBuilder().status(200).entity(messages).build();
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

}
