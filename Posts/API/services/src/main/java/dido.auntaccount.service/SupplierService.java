package dido.auntaccount.service;

import dido.auntaccount.dao.SupplierDAO;
import dido.auntaccount.dto.SupplierDTO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Supplier;
import dido.auntaccount.search.SearchSupplierService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/suppliers")
public class SupplierService {

    @Inject
    private SupplierDAO supplierDAO;

    @Inject
    SearchSupplierService searchSupplierService;

    @GET
    @Path("/test/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveSupplierTest() {

        SupplierDTO supplier = new SupplierDTO();
        supplier.setId(54l);
        supplier.setTags(Arrays.asList("tag1", "tag2", "tag3"));
        String saveSupplier = searchSupplierService.saveSupplier(supplier);
        return Response.status(200).entity(saveSupplier).build();
    }

    @GET
    @Path("/test/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplierTest(@PathParam("param") String supplierId) {
        SupplierDTO supplier = searchSupplierService.getSupplier(supplierId);
        return Response.status(200).entity(supplier).build();
    }


    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplier(@PathParam("param") Long supplierId) {
        Supplier supplier = supplierDAO.find(supplierId);
        return Response.status(200).entity(supplier).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(Supplier supplier) throws Exception {
        Supplier savedSupplier = supplierDAO.save(supplier);
        return Response.status(200).entity(savedSupplier).build();
    }

    @GET
    @Path("/{param}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupplierPosts(@PathParam("param") Long supplierId) {
        Supplier supplier = supplierDAO.find(supplierId);
        if (supplier != null) {
            List<Post> supplierPosts = supplier.getSupplierPosts();
            return Response.status(200).entity(supplierPosts).build();
        }
        return Response.status(200).build();
    }

}
