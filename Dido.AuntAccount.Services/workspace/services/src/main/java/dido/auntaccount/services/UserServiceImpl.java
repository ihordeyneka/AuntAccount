package dido.auntaccount.services;

import dido.auntaccount.dao.App;
import dido.auntaccount.dao.business.UserDao;
import dido.auntaccount.dao.impl.UserDaoImpl;
import dido.auntaccount.entities.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by orysiadeyneka on 19.03.16.
 */
@Path("/user")
public class UserServiceImpl {

    private UserDao dao = new UserDaoImpl();

    @GET
    @Path("/get/{param}")
    public Response getUser(@PathParam("param") Long id) {

        User user = dao.getUserById(id);

        App app = new App();
        String output = "Jersey say : " +  app.getFirstUser();



        //app.createUser();

        return Response.status(200).entity(output).build();

    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMsg(User user) {

        App app = new App();
        app.createUser();

        return Response.status(200).entity("User created").build();

    }
}
