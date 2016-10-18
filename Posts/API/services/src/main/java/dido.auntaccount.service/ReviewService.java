package dido.auntaccount.service;

import dido.auntaccount.dao.ReviewDAO;
import dido.auntaccount.entities.Review;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reviews")
public class ReviewService {

    @Inject
    private ReviewDAO reviewDAO;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReview(@PathParam("param") Long reviewId) {
        Review review = reviewDAO.find(reviewId);
        return Response.status(200).entity(review).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveReview(Review review) throws Exception {
        Review savedReview = reviewDAO.save(review);
        return Response.status(200).entity(savedReview).build();
    }

}
