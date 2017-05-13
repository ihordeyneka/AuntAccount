package dido.auntaccount.search;

import dido.auntaccount.dto.SellerDTO;

import java.util.List;

public interface SearchSellerService {

    String saveSeller(SellerDTO seller);

    SellerDTO getSeller(String id);

    List<Long> getSellerIdsByTags(List<String> tags);

}
