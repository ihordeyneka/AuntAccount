package dido.auntaccount.service;

import dido.auntaccount.dao.OfferDAO;
import dido.auntaccount.entities.Message;
import dido.auntaccount.entities.Offer;
import dido.auntaccount.entities.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/offers")
public class OfferService {

    @Inject
    private OfferDAO offerDAO;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffer(@PathParam("param") Long offerId) {
        Offer offer = offerDAO.find(offerId);
        return Response.status(200).entity(offer).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveOffer(Offer offer) throws Exception {
        Offer savedOffer = offerDAO.save(offer);
        return Response.status(200).entity(savedOffer).build();
    }

    @GET
    @Path("/{param}/supplier")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferSupplier(@PathParam("param") Long offerId) {
        Offer offer = offerDAO.find(offerId);
        User supplier = offer.getSupplier();
        return Response.status(200).entity(supplier).build();
    }

    @GET
    @Path("/{param}/messages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferMessages(@PathParam("param") Long offerId) {
        Offer offer = offerDAO.find(offerId);
        List<Message> messages = offer.getMessages();
        return Response.status(200).entity(messages).build();
    }

}
