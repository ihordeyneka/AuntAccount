package dido.auntaccount.services;

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
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("param") Long id) {
        return dao.getUserById(id);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMsg(User user) {
        dao.saveUser(user);
        return Response.status(200).entity("User created id : " + user.getId()).build();
    }
}
