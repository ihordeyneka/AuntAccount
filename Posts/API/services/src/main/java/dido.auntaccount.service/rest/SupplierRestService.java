package dido.auntaccount.service.rest;

import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Supplier;
import dido.auntaccount.service.business.SupplierService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/suppliers")
public class SupplierRestService {

    @Inject
    SupplierService supplierService;

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplier(@PathParam("param") Long supplierId) {
        Supplier supplier = supplierService.getSupplier(supplierId);
        return Response.status(200).entity(supplier).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveSupplier(Supplier supplier) throws Exception {
        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        return Response.status(200).entity(savedSupplier).build();
    }

    @GET
    @Path("/{param}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplierPosts(@PathParam("param") Long supplierId) {
        List<Post> supplierPosts = supplierService.getSupplierPosts(supplierId);
        return Response.status(200).entity(supplierPosts).build();
    }

}
