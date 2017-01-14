package dido.auntaccount.service.business;

import dido.auntaccount.service.business.impl.PasswordServiceImpl.CannotPerformOperationException;
import dido.auntaccount.service.business.impl.PasswordServiceImpl.InvalidHashException;

public interface PasswordService {

    public String createHash(String password) throws CannotPerformOperationException;

    public boolean verifyPassword(String password, String correctHash) throws CannotPerformOperationException, InvalidHashException;

}
