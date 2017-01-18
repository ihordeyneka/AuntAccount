package dido.auntaccount.service.rest;


import dido.auntaccount.entities.User;
import dido.auntaccount.service.business.PasswordService;
import dido.auntaccount.service.business.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserController {

    @Inject
    private UserService userService;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("param") Long userId) {
        User user = userService.getUser(userId);
        return Response.status(200).entity(user).build();
    }

    @GET
    @Path("/name/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByName(@PathParam("param") String name) {
        User user = userService.findByUserName(name);
        return Response.status(200).entity(user).build();
    }

}
