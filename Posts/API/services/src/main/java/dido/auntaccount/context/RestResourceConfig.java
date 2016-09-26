package dido.auntaccount.context;

import com.google.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

/**
 * Created by orysiadeyneka on 26.09.16.
 */
public class RestResourceConfig extends ResourceConfig {

    @Inject
    public RestResourceConfig(ServiceLocator serviceLocator) {
        // Set package to look for resources in
        packages("dido.auntaccount.service");

        //Creating guice bridge for jersey...

        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);

        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(AppliactionContectListener.injector);

        register(MultiPartFeature.class);
    }

}
