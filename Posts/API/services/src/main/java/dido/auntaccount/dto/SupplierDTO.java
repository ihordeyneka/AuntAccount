package dido.auntaccount.dto;

import dido.auntaccount.entities.Supplier;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierDTO {

    private Long id;
    private UserDTO user;
    private List<TagDTO> tags;
    private List<PostDTO> supplierPosts;

    public SupplierDTO() {
    }

    public SupplierDTO(Supplier supplier) {
        this.id = supplier.getId();
        this.tags = supplier.getSupplierTags().stream().map(TagDTO::new).collect(Collectors.toList());
        this.user = new UserDTO(supplier.getUser());
        this.supplierPosts = supplier.getSupplierPosts().stream().map(PostDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

}
