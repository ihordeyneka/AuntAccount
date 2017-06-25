package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.SellerDTO;
import dido.auntaccount.dto.SellerReviewDTO;
import dido.auntaccount.service.business.PasswordService;
import dido.auntaccount.service.business.SellerReviewService;
import dido.auntaccount.service.business.SellerService;
import dido.auntaccount.service.business.TokenService;
import dido.auntaccount.service.filter.Secured;
import org.apache.commons.io.IOUtils;
import org.apache.http.auth.AuthenticationException;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

import static dido.auntaccount.service.filter.AuthenticationFilter.LOGGED_IN_USER;

@Path("/sellers")
public class SellerController extends Controller {

    @Inject
    SellerService sellerService;

    @Inject
    PasswordService passwordService;

    @Inject
    TokenService tokenService;

    @Inject
    SellerReviewService reviewService;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSeller(@PathParam("param") Long sellerId) {
        SellerDTO seller = sellerService.getSeller(sellerId);
        return getResponseBuilder().entity(seller).build();
    }


    @DELETE
    @Path("/{param}")
    @Secured
    public Response deleteSeller(@PathParam("param") Long sellerId, @HeaderParam(LOGGED_IN_USER) String loggedInUserId) throws Exception {
        SellerDTO seller = sellerService.getSeller(sellerId);
        if (seller.getUserId().equals(Long.valueOf(loggedInUserId))) {
            sellerService.deleteSeller(sellerId);
        } else {
            throw new AuthenticationException("Can't delete seller of not logged in user");
        }
        return getResponseBuilder().entity("{}").build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveSeller(SellerDTO seller) throws Exception {
        final String sellerName = seller.getName();
        final SellerDTO existingSeller = sellerService.getSeller(sellerName);
        if (existingSeller != null) {
            throw new Exception("Seller with specified name already exists " + sellerName);
        }
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

    @GET
    @Path("/{param}/reviews")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSellerReviews(@PathParam("param") Long sellerId) {
        List<SellerReviewDTO> reviews = reviewService.getSellerReviews(sellerId);
        return getResponseBuilder().entity(reviews).build();
    }

    @POST
    @Secured
    @Path("/picture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePicture(@FormDataParam("file_data") InputStream uploadedInputStream, @FormDataParam("sellerId") Long sellerId,
                                  @HeaderParam(LOGGED_IN_USER) String loggedInUserId) throws Exception {
        final SellerDTO seller = sellerService.getSeller(sellerId);
        if (!seller.getUserId().equals(Long.valueOf(loggedInUserId))) {
            throw new AuthenticationException("Can't update seller of not logged in user");
        }
        sellerService.updatePicture(seller, IOUtils.toByteArray(uploadedInputStream));
        return getResponseBuilder().build();
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

    @OPTIONS
    @Path("/picture")
    public Response updatePicturePreflight() throws Exception {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}/reviews")
    public Response getSellerReviewsPreflight(@PathParam("param") Long sellerId) {
        return getResponseBuilder().build();
    }

}
