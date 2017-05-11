package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.service.business.PostService;
import dido.auntaccount.service.filter.Secured;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("/posts")
public class PostController extends Controller {

    @Inject
    PostService postService;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(@PathParam("param") Long postId) {
        PostDTO post = postService.getPost(postId);
        return getResponseBuilder().entity(post).build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response savePost(PostDTO post) throws Exception {
        PostDTO savedPost = postService.savePost(post);
        return getResponseBuilder().entity(savedPost).build();
    }

    @GET
    @Path("/{param}/offers")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostOffers(@PathParam("param") Long postId) {
        List<OfferDTO> offers = postService.getPostOffers(postId);
        return getResponseBuilder().entity(offers).build();
    }

    @POST
    @Path("/upload")
    @Secured
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file_data") InputStream uploadedInputStream, @FormDataParam("postId") Long postId) throws IOException {
        postService.updatePhoto(uploadedInputStream, postId);
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostPreflight(@PathParam("param") Long postId) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/")
    public Response savePostPreflight(PostDTO post) throws Exception {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}/offers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostOffersPreflight() {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/upload")
    public Response uploadFilePreflight() throws IOException {
        return getResponseBuilder().build();
    }


}