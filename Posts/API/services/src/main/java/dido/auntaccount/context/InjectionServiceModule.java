package dido.auntaccount.context;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import dido.auntaccount.dido.auntaccount.utils.PropertiesHandler;
import dido.auntaccount.search.SearchSupplierService;
import dido.auntaccount.search.client.SearchClientService;
import dido.auntaccount.search.client.impl.SearchClientServiceImpl;
import dido.auntaccount.search.impl.SearchSupplierServiceImpl;
import dido.auntaccount.service.business.*;
import dido.auntaccount.service.business.impl.*;

public class InjectionServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SearchClientService.class).to(SearchClientServiceImpl.class);
        bind(SearchSupplierService.class).to(SearchSupplierServiceImpl.class);
        bind(MessageService.class).to(MessageServiceImpl.class);
        bind(OfferService.class).to(OfferServiceImpl.class);
        bind(PostService.class).to(PostServiceImpl.class);
        bind(ReviewService.class).to(ReviewServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(SupplierService.class).to(SupplierServiceImpl.class);
        bind(PasswordService.class).to(PasswordServiceImpl.class);
        bind(TokenService.class).to(TokenServiceImpl.class);

        Names.bindProperties(binder(), PropertiesHandler.initProperties());
    }
}