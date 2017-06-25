package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.SellerReviewDTO;
import dido.auntaccount.service.business.SellerReviewService;
import dido.auntaccount.service.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/seller/reviews")
public class SellerReviewController extends Controller {

    @Inject
    SellerReviewService reviewService;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReview(@PathParam("param") Long reviewId) {
        SellerReviewDTO review = reviewService.getReview(reviewId);
        return Response.status(200).entity(review).build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveReview(SellerReviewDTO review) throws Exception {
        SellerReviewDTO savedReview = reviewService.saveReview(review);
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
    public Response saveReviewPreflight(SellerReviewDTO review) throws Exception {
        return getResponseBuilder().build();
    }

}
