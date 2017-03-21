package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.SupplierDAO;
import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.SupplierDTO;
import dido.auntaccount.dto.TagDTO;
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
    public SupplierDTO getSupplier(Long supplierId) {
        Supplier supplier = supplierDAO.find(supplierId);
        return new SupplierDTO(supplier);
    }

    @Override
    public SupplierDTO saveSupplier(SupplierDTO supplier) {
        Supplier savedSupplier = null;
        try {
            savedSupplier = supplierDAO.save(supplier.buildEntity());
            searchSupplierService.saveSupplier(new SupplierDTO(savedSupplier));
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save supplier", e);
        }
        return new SupplierDTO(savedSupplier);
    }

    @Override
    public List<PostDTO> getSupplierPosts(Long supplierId) {
        Supplier supplier = supplierDAO.find(supplierId);
        if (supplier != null) {
            List<Post> supplierPosts = supplier.getSupplierPosts();
            return supplierPosts.stream().map(PostDTO::new).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public void savePostForSuppliers(PostDTO post) {
        List<TagDTO> postTags = post.getTags();
        List<String> tags = postTags.stream().map(TagDTO::getTag).collect(Collectors.toList());

        List<Long> supplierIds = searchSupplierService.getSupplierIdsByTags(tags);
        supplierIds.stream().forEach(s -> supplierDAO.addSupplierPost(s, post.buildEntity()));
    }
}
