package dido.auntaccount.dto;

import dido.auntaccount.entities.Supplier;
import dido.auntaccount.entities.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierDTO {

    private static final Logger logger = LogManager.getLogger(SupplierDTO.class);

    private Long id;
    private List<String> tags;

    public SupplierDTO() {
    }

    public SupplierDTO(Long id, List<String> tags) {
        this.id = id;
        this.tags = tags;
    }

    public SupplierDTO(Supplier supplier) {
        this.id = supplier.getId();
        this.tags = supplier.getSupplierTags().stream().map(Tag::getTag).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
