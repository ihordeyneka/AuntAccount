package dido.auntaccount.search.client.impl;

import dido.auntaccount.search.client.SearchClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

public class SearchClientServiceImpl implements SearchClientService {

    private static final Logger logger = LogManager.getLogger(SearchClientServiceImpl.class);

    private Client client;

    public Client getClient() {
        if (client == null) {
            client = createClient();
        }

        return client;
    }

    protected Client createClient() {
        if (client == null) {

            if (logger.isDebugEnabled()) {
                logger.debug("Creating client for Search!");
            }

            try {
                TransportClient transportClient = new PreBuiltTransportClient(Settings.EMPTY)
                        .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

                if (transportClient.connectedNodes().size() == 0) {
                    logger.error("There are no active nodes available for the transport, it will be automatically added once nodes are live!");
                }

                client = transportClient;

            } catch (Exception ex) {
                logger.error("Error occured while creating search client!", ex);
            }
        }
        return client;
    }

}
