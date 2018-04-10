package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.RefreshTokenDAO;
import dido.auntaccount.dao.TokenDAO;
import dido.auntaccount.dido.auntaccount.utils.CacheMap;
import dido.auntaccount.dto.RefreshTokenDTO;
import dido.auntaccount.dto.TokenDTO;
import dido.auntaccount.entities.RefreshToken;
import dido.auntaccount.entities.Token;
import dido.auntaccount.service.business.TokenService;
import dido.auntaccount.service.rest.Tokens;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.sql.Date;

public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LogManager.getLogger(TokenServiceImpl.class);
    private static final CacheMap<String, Token> tokenCacheMap = new CacheMap<>(1000);

    private static final int REFRESH_EXPIRATION_YEARS = 1;
    private static final int ACCESS_EXPIRATION_HOURS = 1;

    @Inject
    TokenDAO tokenDAO;

    @Inject
    RefreshTokenDAO refreshTokenDAO;

    @Override
    public TokenDTO getToken(String token) {
        Token foundToken = tokenCacheMap.containsKey(token) ?
                tokenCacheMap.get(token) : tokenDAO.find(token);

        return new TokenDTO(foundToken);
    }

    @Override
    public RefreshTokenDTO getRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenDAO.find(token);
        return new RefreshTokenDTO(refreshToken);
    }

    @Override
    public RefreshTokenDTO saveRefreshToken(String token, long expirationDate, long userId) {
        RefreshToken refreshToken = new RefreshToken(token, new Date(expirationDate), userId);
        RefreshToken savedRefreshToken = null;
        try {
            savedRefreshToken = refreshTokenDAO.save(refreshToken);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save refresh token", e);
        }
        return new RefreshTokenDTO(savedRefreshToken);
    }

    @Override
    public TokenDTO saveAccessToken(String token, long expirationDate, long userId) {
        Token accessToken = new Token(token, new Date(expirationDate), userId);
        Token savedToken = null;
        try {
            savedToken = tokenDAO.save(accessToken);
            tokenCacheMap.put(token, savedToken);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save token", e);
        }
        return new TokenDTO(savedToken);
    }

    @Override
    public void deleteToken(String token) {
        try {
            tokenDAO.delete(token);
            tokenCacheMap.remove(token);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't delete token", e);
        }
    }

    public Tokens issueNativeTokens(Long userId) throws OAuthSystemException {

        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

        String accessToken = oauthIssuerImpl.accessToken();
        String refreshToken = oauthIssuerImpl.refreshToken();

        long accessExpirationDate = getAccessExpirationDate();
        saveAccessToken(accessToken, accessExpirationDate, userId);

        long refreshExpirationDate = getRefreshExpirationDate();
        saveRefreshToken(refreshToken, refreshExpirationDate, userId);

        return new Tokens(accessToken, accessExpirationDate, refreshToken, refreshExpirationDate);
    }

    public long getAccessExpirationDate() {
        return DateTime.now().plusHours(ACCESS_EXPIRATION_HOURS).getMillis();
    }

    private static long getRefreshExpirationDate() {
        return DateTime.now().plusYears(REFRESH_EXPIRATION_YEARS).getMillis();
    }
}
