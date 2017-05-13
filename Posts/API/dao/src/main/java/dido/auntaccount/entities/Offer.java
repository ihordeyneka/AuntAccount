package dido.auntaccount.entities;

import javax.persistence.*;

@Entity
@SqlResultSetMapping(
        name = "OfferReplyCountMapping",
        entities = {@EntityResult(entityClass = Offer.class)},
        columns = @ColumnResult(name = "replyCount", type = Integer.class))
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "SellerId")
    private Seller seller;

    private Long postId;

    public Long getId() {
        return id;
    }

    public Offer setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getPostId() {
        return postId;
    }

    public Offer setPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    public Seller getSeller() {
        return seller;
    }

    public Offer setSeller(Seller seller) {
        this.seller = seller;
        return this;
    }

}
