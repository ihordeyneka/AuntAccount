package dido.auntaccount.entities;

import javax.persistence.Entity;

/**
 * Created by orysiadeyneka on 24.09.16.
 */

@Entity
public class Post {

    private long id;

    private String description;

    public String getDescription() {
        return description;
    }

    public Post setDescription(String description) {
        this.description = description;
        return this;
    }

}
