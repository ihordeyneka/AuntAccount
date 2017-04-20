package dido.auntaccount.service.business;

import dido.auntaccount.dto.RefreshTokenDTO;
import dido.auntaccount.dto.TokenDTO;

public interface TokenService {

    TokenDTO getToken(String token);

    RefreshTokenDTO getRefreshToken(String token);

    RefreshTokenDTO saveRefreshToken(String token, long expirationDate, long userId);

    TokenDTO saveAccessToken(String token, long expirationDate, long userId);

}
