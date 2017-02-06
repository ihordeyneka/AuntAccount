package dido.auntaccount.service.business;

import dido.auntaccount.entities.Token;

public interface TokenService {

    Token getToken(String token);

    Token saveToken(String token, long expiresIn);

}
