package dido.auntaccount.services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import dido.auntaccount.dao.App;
import dido.auntaccount.entities.User;

@Path("/test")
public class TestService {
 
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		App app = new App();
		String output = "Jersey say : " +  app.getFirstUser();
		
		
		
		//app.createUser();

		return Response.status(200).entity(output).build();
 
	}

	@GET
	@Path("/create")
	public Response createMsg() {

		App app = new App();
		app.createUser();

		return Response.status(200).entity("User created").build();

	}
 
}
