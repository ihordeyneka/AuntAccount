package dido.auntaccount.context;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dido.auntaccount.inject.InjectionDAOModule;

import com.google.inject.servlet.GuiceServletContextListener;

//@WebListener
public class ApplicationContextListener extends GuiceServletContextListener {

    static Injector injector;

    protected Injector getInjector() {
        if (injector == null) {
            injector = Guice.createInjector(new InjectionDAOModule(), new InjectionServiceModule());
        }

        return injector;
    }
}
