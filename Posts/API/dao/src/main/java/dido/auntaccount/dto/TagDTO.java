package dido.auntaccount.dto;

import dido.auntaccount.entities.Tag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TagDTO implements DTO<Tag> {

    private Long id;
    private String tag;

    public TagDTO() {
    }

    public TagDTO(String tag) {
        this.tag = tag;
    }

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.tag = tag.getTag();
    }

    @Override
    public Tag buildEntity() {
        return new Tag(tag)
                .setId(id);
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

    public static List<TagDTO> getTags(String tagsList) {
        List<String> tags = parsePostTags(tagsList);
        return tags.stream().map(TagDTO::new).collect(Collectors.toList());
    }

    public static List<String> parsePostTags(String tags) {
        return tags != null && !tags.isEmpty() ? Arrays.asList(tags.split(",")) : Collections.emptyList();
    }
}

