package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.SellerReviewDTO;
import dido.auntaccount.service.business.SellerReviewService;
import dido.auntaccount.service.filter.Secured;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.Date;

import static dido.auntaccount.service.filter.AuthenticationFilter.LOGGED_IN_USER;

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
        return getResponseBuilder().entity(review).build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveReview(SellerReviewDTO review, @HeaderParam(LOGGED_IN_USER) String loggedInUserId) throws Exception {
        final Long userId = Long.valueOf(loggedInUserId);
        review.setUserId(userId);
        review.setCreationDate(new Date(DateTime.now().getMillis()));
        final SellerReviewDTO existingReview = reviewService.getUserReview(review.getSellerId(), userId);
        SellerReviewDTO savedReview = null;
        if (existingReview == null) {
            savedReview = reviewService.saveReview(review);
        } else {
            review.setId(existingReview.getId());
            savedReview = reviewService.updateReview(review);
        }
        return getResponseBuilder().entity(savedReview).build();
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
    public Response saveReviewPreflight() throws Exception {
        return getResponseBuilder().build();
    }

}
