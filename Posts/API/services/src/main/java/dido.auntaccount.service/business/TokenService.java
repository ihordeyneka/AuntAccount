package dido.auntaccount.service.business;

import dido.auntaccount.dto.RefreshTokenDTO;
import dido.auntaccount.dto.TokenDTO;
import dido.auntaccount.service.rest.Tokens;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

public interface TokenService {

    TokenDTO getToken(String token);

    RefreshTokenDTO getRefreshToken(String token);

    RefreshTokenDTO saveRefreshToken(String token, long expirationDate, long userId);

    TokenDTO saveAccessToken(String token, long expirationDate, long userId);

    void deleteToken(String token);

    Tokens issueNativeTokens(Long userId) throws OAuthSystemException;

    long getAccessExpirationDate();
}
