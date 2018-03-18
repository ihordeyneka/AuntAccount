package dido.auntaccount.entities;

import javax.persistence.*;

@Entity
@Table(name="Tag")
public class Tag {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String tag;

    public Tag(){

    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public Tag setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public Tag setTag(String tag) {
        this.tag = tag;
        return this;
    }
}

