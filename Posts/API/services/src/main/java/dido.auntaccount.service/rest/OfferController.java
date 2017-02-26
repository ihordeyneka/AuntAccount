package dido.auntaccount.service.rest;

import dido.auntaccount.dto.MessageDTO;
import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.dto.SupplierDTO;
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
public class OfferController extends Controller {

    @Inject
    OfferService service;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffer(@PathParam("param") Long offerId) {
        OfferDTO offer = service.getOffer(offerId);
        return getResponseBuilder().status(200).entity(offer).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveOffer(OfferDTO offer) throws Exception {
        OfferDTO savedOffer = service.saveOffer(offer);
        return getResponseBuilder().status(200).entity(savedOffer).build();
    }

    @GET
    @Path("/{param}/supplier")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferSupplier(@PathParam("param") Long offerId) {
        SupplierDTO supplier = service.getOfferSupplier(offerId);
        return getResponseBuilder().status(200).entity(supplier).build();
    }

    @GET
    @Path("/{param}/messages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfferMessages(@PathParam("param") Long offerId) {
        List<MessageDTO> messages = service.getOfferMessages(offerId);
        return getResponseBuilder().status(200).entity(messages).build();
    }

}
