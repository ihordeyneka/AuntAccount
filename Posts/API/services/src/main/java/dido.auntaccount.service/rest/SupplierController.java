package dido.auntaccount.service.rest;

import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.SupplierDTO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Supplier;
import dido.auntaccount.service.business.PasswordService;
import dido.auntaccount.service.business.SupplierService;
import dido.auntaccount.service.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/suppliers")
public class SupplierController {

    @Inject
    SupplierService supplierService;

    @Inject
    PasswordService passwordService;

    @GET
    @Path("/{param}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplier(@PathParam("param") Long supplierId) {
        SupplierDTO supplier = supplierService.getSupplier(supplierId);
        return Response.status(200).entity(supplier).build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveSupplier(SupplierDTO supplier) throws Exception {
        String hashedPassword = passwordService.createHash(supplier.getPassword());
        supplier.setPassword(hashedPassword);
        SupplierDTO savedSupplier = supplierService.saveSupplier(supplier);
        return Response.status(200).entity(savedSupplier).build();
    }

    @GET
    @Path("/{param}/posts")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplierPosts(@PathParam("param") Long supplierId) {
        List<PostDTO> supplierPosts = supplierService.getSupplierPosts(supplierId);
        return Response.status(200).entity(supplierPosts).build();
    }

}
