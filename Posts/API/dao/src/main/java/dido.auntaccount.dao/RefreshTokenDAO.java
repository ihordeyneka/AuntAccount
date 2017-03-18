package dido.auntaccount.dao;

import dido.auntaccount.entities.RefreshToken;

public interface RefreshTokenDAO {

    public RefreshToken find(String token);

    public RefreshToken save(RefreshToken refreshToken) throws Exception;

    public void delete(RefreshToken refreshToken) throws Exception;

}
