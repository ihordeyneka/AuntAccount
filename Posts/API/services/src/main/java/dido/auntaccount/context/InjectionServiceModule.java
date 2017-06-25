package dido.auntaccount.context;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import dido.auntaccount.dido.auntaccount.utils.PropertiesHandler;
import dido.auntaccount.search.SearchSellerService;
import dido.auntaccount.search.client.SearchClientService;
import dido.auntaccount.search.client.impl.SearchClientServiceImpl;
import dido.auntaccount.search.impl.SearchSellerServiceImpl;
import dido.auntaccount.service.business.*;
import dido.auntaccount.service.business.impl.*;

public class InjectionServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SearchClientService.class).to(SearchClientServiceImpl.class);
        bind(SearchSellerService.class).to(SearchSellerServiceImpl.class);
        bind(MessageService.class).to(MessageServiceImpl.class);
        bind(OfferService.class).to(OfferServiceImpl.class);
        bind(PostService.class).to(PostServiceImpl.class);
        bind(SellerReviewService.class).to(SellerReviewServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(SellerService.class).to(SellerServiceImpl.class);
        bind(PasswordService.class).to(PasswordServiceImpl.class);
        bind(TokenService.class).to(TokenServiceImpl.class);
        bind(TagService.class).to(TagServiceImpl.class);
        bind(LocationService.class).to(LocationServiceImpl.class);

        Names.bindProperties(binder(), PropertiesHandler.initProperties());
    }
}
