package dido.auntaccount.service.rest;

import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.Supplier;
import dido.auntaccount.service.business.OfferService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/offers")
public class OfferController {

    @Inject
    OfferService service;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffer(@PathParam("param") Long offerId) {
        Offer offer = service.getOffer(offerId);
        return Response.status(200).entity(offer).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveOffer(Offer offer) throws Exception {
        Offer savedOffer = service.saveOffer(offer);
        return Response.status(200).entity(savedOffer).build();
    }

    @GET
    @Path("/{param}/supplier")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferSupplier(@PathParam("param") Long offerId) {
        Supplier supplier = service.getOfferSupplier(offerId);
        return Response.status(200).entity(supplier).build();
    }

    @GET
    @Path("/{param}/messages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferMessages(@PathParam("param") Long offerId) {
        List<Message> messages = service.getOfferMessages(offerId);
        return Response.status(200).entity(messages).build();
    }

}
