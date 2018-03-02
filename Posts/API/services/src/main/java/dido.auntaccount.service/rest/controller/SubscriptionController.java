package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.SubscriptionDTO;
import dido.auntaccount.service.business.LocationService;
import dido.auntaccount.service.business.SubscriptionService;
import dido.auntaccount.service.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/subscriptions")
public class SubscriptionController extends Controller {

    @Inject
    SubscriptionService subscriptionService;

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveSubscription(SubscriptionDTO subscription) throws Exception {
        subscriptionService.saveSubscription(subscription);
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/")
    public Response saveSubscriptionPreflight(SubscriptionDTO subscription) throws Exception {
        return getResponseBuilder().build();
    }

}
