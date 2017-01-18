package dido.auntaccount.service.rest;

import dido.auntaccount.entities.Customer;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Review;
import dido.auntaccount.service.business.CustomerService;
import dido.auntaccount.service.business.PasswordService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customers")
public class CustomerController {

    @Inject
    private CustomerService customerService;

    @Inject
    PasswordService passwordService;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("param") Long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        return Response.status(200).entity(customer).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCustomer(Customer customer) throws Exception {
        String hashedPassword = passwordService.createHash(customer.getPassword());
        customer.setPassword(hashedPassword);
        Customer savedCustomer = customerService.saveCustomer(customer);
        return Response.status(200).entity(savedCustomer).build();
    }

    @GET
    @Path("/{param}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerPosts(@PathParam("param") Long customerId) {
        List<Post> posts = customerService.getCustomerPosts(customerId);
        return Response.status(200).entity(posts).build();
    }

    @GET
    @Path("/{param}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerReviews(@PathParam("param") Long customerId) {
        List<Review> reviews = customerService.getCustomerReviews(customerId);
        return Response.status(200).entity(reviews).build();
    }

}
