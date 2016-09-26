package dido.auntaccount.inject;

import com.google.inject.AbstractModule;
import dido.auntaccount.dao.PostDAO;
import dido.auntaccount.dao.impl.PostDAOImpl;
import dido.auntaccount.db.EntityManagerProvider;

import javax.persistence.EntityManager;

/**
 * Created by orysiadeyneka on 24.09.16.
 */
public class InjectionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EntityManager.class).toProvider(EntityManagerProvider.class);
        bind(PostDAO.class).to(PostDAOImpl.class);
    }
}
