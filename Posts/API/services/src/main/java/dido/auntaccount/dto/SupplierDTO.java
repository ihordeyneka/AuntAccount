package dido.auntaccount.dto;

import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Supplier;
import dido.auntaccount.entities.Tag;
import dido.auntaccount.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierDTO implements DTO<Supplier> {

    private Long id;
    private UserDTO user;
    private List<TagDTO> tags;
    private List<PostDTO> supplierPosts;

    public SupplierDTO() {
    }

    @Override
    public Supplier buildEntity() {
        User entityUser = new User().setId(user.getId());
        List<Tag> entityTags = tags.stream().map(TagDTO::buildEntity).collect(Collectors.toList());
        List<Post> entityPosts = supplierPosts.stream().map(PostDTO::buildEntity).collect(Collectors.toList());
        return new Supplier()
                .setId(id)
                .setUser(entityUser)
                .setSupplierTags(entityTags)
                .setSupplierPosts(entityPosts);
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


    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<PostDTO> getSupplierPosts() {
        return supplierPosts;
    }

    public void setSupplierPosts(List<PostDTO> supplierPosts) {
        this.supplierPosts = supplierPosts;
    }
}
