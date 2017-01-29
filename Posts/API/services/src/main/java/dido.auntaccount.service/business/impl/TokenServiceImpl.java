package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.TokenDAO;
import dido.auntaccount.dido.auntaccount.utils.CacheMap;
import dido.auntaccount.entities.Token;
import dido.auntaccount.service.business.TokenService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.sql.Date;

public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LogManager.getLogger(TokenServiceImpl.class);
    private static final CacheMap<String, Long> tokenCacheMap = new CacheMap<>(1000);

    @Inject
    TokenDAO tokenDAO;

    @Override
    public Token getToken(String token) {
        if (tokenCacheMap.containsKey(token)) {
            Long expirationMillis = tokenCacheMap.get(token);
            return new Token(token, new Date(expirationMillis));
        }
        return tokenDAO.find(token);
    }

    @Override
    public Token saveToken(String token, long expiresIn) {
        DateTime now = DateTime.now();
        DateTime expirationDate = now.plus(expiresIn);
        long expirationDateMillis = expirationDate.getMillis();
        Token accessToken = new Token(token, new Date(expirationDateMillis));
        Token savedToken = null;
        try {
            savedToken = tokenDAO.save(accessToken);
            tokenCacheMap.put(token, expirationDateMillis);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Couldn't save token", e);
        }
        return savedToken;
    }

}
