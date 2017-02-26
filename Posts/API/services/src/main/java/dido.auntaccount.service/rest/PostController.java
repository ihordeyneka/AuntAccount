package dido.auntaccount.service.rest;

import dido.auntaccount.dto.OfferDTO;
import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.service.business.PostService;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("/posts")
public class PostController {

    @Inject
    PostService postService;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(@PathParam("param") Long postId) {
        PostDTO post = postService.getPost(postId);
        return Response.status(200).entity(post).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response savePost(PostDTO post) throws Exception {
        PostDTO savedPost = postService.savePost(post);
        return Response.status(200).entity(savedPost).build();
    }

    @GET
    @Path("/{param}/offers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostOffers(@PathParam("param") Long postId) {
        List<OfferDTO> offers = postService.getPostOffers(postId);
        return Response.status(200).entity(offers).build();
    }


    @POST
    @Path("/upload/photo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormParam("postId") Long postId) throws IOException {
        postService.updatePhoto(uploadedInputStream, postId);
        return Response.status(200).build();
    }

}