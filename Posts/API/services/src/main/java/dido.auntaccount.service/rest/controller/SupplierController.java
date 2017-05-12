package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.SupplierDTO;
import dido.auntaccount.service.business.PasswordService;
import dido.auntaccount.service.business.SupplierService;
import dido.auntaccount.service.filter.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/suppliers")
public class SupplierController extends Controller {

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
        return getResponseBuilder().entity(supplier).build();
    }

    @POST
    @Path("/")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveSupplier(SupplierDTO supplier) throws Exception {
        SupplierDTO savedSupplier = supplierService.saveSupplier(supplier);
        return getResponseBuilder().entity(savedSupplier).build();
    }

    @GET
    @Path("/{param}/posts")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplierPosts(@PathParam("param") Long supplierId) {
        List<PostDTO> supplierPosts = supplierService.getSupplierPosts(supplierId);
        return getResponseBuilder().entity(supplierPosts).build();
    }

    @OPTIONS
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplierPreflight(@PathParam("param") Long supplierId) {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveSupplierPreflight(SupplierDTO supplier) throws Exception {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/{param}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplierPostsPreflight(@PathParam("param") Long supplierId) {
        return getResponseBuilder().build();
    }

}
