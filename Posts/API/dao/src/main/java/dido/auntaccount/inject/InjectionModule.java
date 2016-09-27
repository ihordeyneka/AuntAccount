package dido.auntaccount.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import dido.auntaccount.dao.PostDAO;
import dido.auntaccount.dao.impl.PostDAOImpl;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * Created by orysiadeyneka on 24.09.16.
 */
public class InjectionModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new JpaPersistModule("auntaccount"));
        bind(JPAInitializer.class).asEagerSingleton();
        bind(PostDAO.class).to(PostDAOImpl.class);
    }

    @Singleton
    public static class JPAInitializer {

        @Inject
        public JPAInitializer(final PersistService service) {
            service.start();
        }

    }

}