package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.SellerDTO;
import dido.auntaccount.service.business.PasswordService;
import dido.auntaccount.service.business.SellerService;
import dido.auntaccount.service.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/sellers")
public class SellerController extends Controller {

    @Inject
    SellerService sellerService;

    @Inject
    PasswordService passwordService;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeller(@PathParam("param") Long sellerId) {
        SellerDTO seller = sellerService.getSeller(sellerId);
        return getResponseBuilder().entity(seller).build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveSeller(SellerDTO seller) throws Exception {
        SellerDTO savedSeller = sellerService.saveSeller(seller);
        return getResponseBuilder().entity(savedSeller).build();
    }

    @GET
    @Path("/{param}/posts")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSellerPosts(@PathParam("param") Long sellerId) {
        List<PostDTO> sellerPosts = sellerService.getSellerPosts(sellerId);
        return getResponseBuilder().entity(sellerPosts).build();
    }

    @OPTIONS
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSellerPreflight(@PathParam("param") Long sellerId) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveSellerPreflight(SellerDTO seller) throws Exception {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSellerPostsPreflight(@PathParam("param") Long sellerId) {
        return getResponseBuilder().build();
    }

}
