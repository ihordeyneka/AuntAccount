package dido.auntaccount.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import dido.auntaccount.dao.*;
import dido.auntaccount.dao.impl.*;
import com.google.inject.persist.jpa.JpaPersistModule;

public class InjectionDAOModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new JpaPersistModule("auntaccount"));
        bind(JPAInitializer.class).asEagerSingleton();
        bind(PostDAO.class).to(PostDAOImpl.class);
        bind(OfferDAO.class).to(OfferDAOImpl.class);
        bind(UserDAO.class).to(UserDAOImpl.class);
        bind(MessageDAO.class).to(MessageDAOImpl.class);
        bind(ReviewDAO.class).to(ReviewDAOImpl.class);
        bind(SellerDAO.class).to(SellerDAOImpl.class);
        bind(TokenDAO.class).to(TokenDAOImpl.class);
        bind(RefreshTokenDAO.class).to(RefreshTokenDAOImpl.class);
        bind(CountryDAO.class).to(CountryDAOImpl.class);
    }

    @Singleton
    public static class JPAInitializer {

        @Inject
        public JPAInitializer(final PersistService service) {
            service.start();
        }

    }

}
