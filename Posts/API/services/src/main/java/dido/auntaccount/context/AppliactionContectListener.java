package dido.auntaccount.context;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dido.auntaccount.inject.InjectionModule;

import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.annotation.WebListener;

/**
 * Created by orysiadeyneka on 24.09.16.
 */

//@WebListener
public class AppliactionContectListener extends GuiceServletContextListener {

    static Injector injector;

    protected Injector getInjector() {
        if (injector == null) {
            injector = Guice.createInjector(new InjectionModule());
        }

        return injector;
    }
}
