package dido.auntaccount.dao;

import dido.auntaccount.entities.Token;

public interface TokenDAO {

    Token find(String token);

    Token save(Token token) throws Exception;

    void delete(Token token) throws Exception;

    void deleteExpiredTokens();

}
