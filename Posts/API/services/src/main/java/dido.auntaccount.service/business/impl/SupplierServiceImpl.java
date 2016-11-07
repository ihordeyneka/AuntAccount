package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.SupplierDAO;
import dido.auntaccount.dto.SupplierDTO;
import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Supplier;
import dido.auntaccount.entities.Tag;
import dido.auntaccount.search.SearchSupplierService;
import dido.auntaccount.service.business.SupplierService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierServiceImpl implements SupplierService {

    private static final Logger logger = LogManager.getLogger(SupplierServiceImpl.class);

    @Inject
    private SupplierDAO supplierDAO;

    @Inject
    SearchSupplierService searchSupplierService;

    @Override
    public Supplier getSupplier(Long supplierId) {
        return supplierDAO.find(supplierId);
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        Supplier savedSupplier = null;
        try {
            savedSupplier = supplierDAO.save(supplier);
            searchSupplierService.saveSupplier(new SupplierDTO(savedSupplier));
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save supplier", e);
        }
        return savedSupplier;
    }

    @Override
    public List<Post> getSupplierPosts(Long supplierId) {
        Supplier supplier = supplierDAO.find(supplierId);
        if (supplier != null) {
            return supplier.getSupplierPosts();
        }
        return new ArrayList<>();
    }

    public void savePostForSuppliers(Post post) {
        List<Tag> postTags = post.getPostTags();
        List<String> tags = postTags.stream().map(Tag::getTag).collect(Collectors.toList());

        List<Long> supplierIds = searchSupplierService.getSupplierIdsByTags(tags);
        supplierIds.stream().forEach(s -> supplierDAO.addSupplierPost(s, post));
    }
}
