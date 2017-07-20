package dido.auntaccount.service.business;

import dido.auntaccount.dto.PostDTO;
import dido.auntaccount.dto.SellerDTO;

import java.util.List;

public interface SellerService {

    SellerDTO getSeller(Long sellerId);

    SellerDTO getSeller(String name);

    SellerDTO saveSeller(SellerDTO seller);

    List<PostDTO> getSellerPosts(Long sellerId);

    void savePostForSellers(PostDTO post);

    void updatePicture(SellerDTO seller, byte[] picture) throws Exception;

    void deleteSeller(Long sellerId) throws Exception;

    void updateSeller(SellerDTO seller);
}
