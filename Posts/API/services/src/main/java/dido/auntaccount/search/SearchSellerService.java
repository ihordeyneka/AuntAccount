package dido.auntaccount.search;

import dido.auntaccount.dto.LocationDTO;
import dido.auntaccount.dto.SellerDTO;

import java.util.List;

public interface SearchSellerService {

    String saveSeller(SellerDTO seller);

    SellerDTO getSeller(String id);

    List<Long> getSellerIdsByTagsAndLocation(List<String> tags, LocationDTO location);

    void updateSeller(SellerDTO sellerDTO);

}
