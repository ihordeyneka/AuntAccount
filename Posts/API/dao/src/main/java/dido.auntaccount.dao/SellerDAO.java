package dido.auntaccount.dao;

import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Seller;

public interface SellerDAO {

    public Seller find(Long sellerId);

    public Seller save(Seller seller) throws Exception;

    public void addSellerPost(Long sellerId, Post post);

}
