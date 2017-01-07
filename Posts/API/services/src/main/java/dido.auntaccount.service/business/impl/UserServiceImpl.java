package dido.auntaccount.service.business.impl;

import dido.auntaccount.dao.UserDAO;
import dido.auntaccount.entities.User;
import dido.auntaccount.service.business.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Inject
    private UserDAO userDAO;

    @Override
    public User getUser(Long userId) {
        return userDAO.find(userId);
    }

    @Override
    public User findByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }

}
