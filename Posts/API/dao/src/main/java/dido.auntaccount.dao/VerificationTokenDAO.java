package dido.auntaccount.dao;

import dido.auntaccount.entities.VerificationToken;

public interface VerificationTokenDAO {

    VerificationToken find(String token);

    VerificationToken save(VerificationToken token) throws Exception;
}
