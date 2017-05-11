package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.ReviewDTO;
import dido.auntaccount.entities.Review;
import dido.auntaccount.service.business.ReviewService;
import dido.auntaccount.service.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reviews")
public class ReviewController extends Controller {

    @Inject
    ReviewService reviewService;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReview(@PathParam("param") Long reviewId) {
        ReviewDTO review = reviewService.getReview(reviewId);
        return Response.status(200).entity(review).build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveReview(ReviewDTO review) throws Exception {
        ReviewDTO savedReview = reviewService.saveReview(review);
        return Response.status(200).entity(savedReview).build();
    }

    @OPTIONS
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReviewPreflight(@PathParam("param") Long reviewId) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveReviewPreflight(ReviewDTO review) throws Exception {
        return getResponseBuilder().build();
    }

}
