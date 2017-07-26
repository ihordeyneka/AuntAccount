package dido.auntaccount.dao;

import dido.auntaccount.entities.Post;
import dido.auntaccount.entities.Seller;

public interface SellerDAO {

    public Seller find(Long sellerId);

    public Seller save(Seller seller) throws Exception;

    public void addSellerPost(Long sellerId, Post post);

    void deleteSeller(Long sellerId) throws Exception;

    Seller findByName(String name);

    boolean hasSellers(Long userId);

    Seller updateSeller(Seller seller);
}
