package dido.auntaccount.context;

import com.google.inject.AbstractModule;
import dido.auntaccount.search.SearchSupplierService;
import dido.auntaccount.search.client.SearchClientService;
import dido.auntaccount.search.client.impl.SearchClientServiceImpl;
import dido.auntaccount.search.impl.SearchSupplierServiceImpl;

public class InjectionServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SearchClientService.class).to(SearchClientServiceImpl.class);
        bind(SearchSupplierService.class).to(SearchSupplierServiceImpl.class);
    }
}
