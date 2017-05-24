package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.SellerDTO;
import dido.auntaccount.dto.TokenDTO;
import dido.auntaccount.service.business.PasswordService;
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

@Path("/sellers")
public class SellerController extends Controller {

    @Inject
    SellerService sellerService;

    @Inject
    PasswordService passwordService;

    @Inject
    TokenService tokenService;

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
    public Response deleteSeller(@PathParam("param") Long sellerId, @HeaderParam(TokenController.ACCESS_TOKEN) String token) throws Exception {
        TokenDTO foundToken = tokenService.getToken(token);
        final Long userId = foundToken.getUserId();
        SellerDTO seller = sellerService.getSeller(sellerId);
        if (seller.getUserId().equals(userId)) {
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

    @POST
    @Secured
    @Path("/picture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePicture(@FormDataParam("file_data") InputStream uploadedInputStream, @FormDataParam("sellerId") Long sellerId, @HeaderParam(TokenController.ACCESS_TOKEN) String token) throws Exception {
        TokenDTO foundToken = tokenService.getToken(token);
        final SellerDTO seller = sellerService.getSeller(sellerId);
        if (!seller.getUserId().equals(foundToken.getUserId())) {
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

}
