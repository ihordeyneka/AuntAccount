package dido.auntaccount.dto;

import dido.auntaccount.entities.Tag;

public class TagDTO implements DTO<Tag> {

    private Long id;
    private String tag;

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.tag = tag.getTag();
    }

    @Override
    public Tag buildEntity() {
        return new Tag()
                .setId(id)
                .setTag(tag);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }
}

