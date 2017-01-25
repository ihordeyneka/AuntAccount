package dido.auntaccount.service.rest;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/auth")
public class AuthController {

    private static final String FACEBOOK_CLIENT_ID = "*";

    @GET
    public Response authorize() throws URISyntaxException, OAuthSystemException {


        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(OAuthProviderType.FACEBOOK.getAuthzEndpoint())
                .setClientId(FACEBOOK_CLIENT_ID)
                .setRedirectURI("http://localhost:8080/api/service/clienttoken")
                .buildQueryMessage();
        return Response.seeOther(URI.create(request.getLocationUri())).build();
    }

}
