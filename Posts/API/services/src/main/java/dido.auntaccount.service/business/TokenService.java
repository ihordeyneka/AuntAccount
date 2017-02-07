package dido.auntaccount.service.business;

import dido.auntaccount.dto.TokenDTO;
import dido.auntaccount.entities.Token;

public interface TokenService {

    TokenDTO getToken(String token);

    TokenDTO saveToken(String token, long expiresIn);

}
