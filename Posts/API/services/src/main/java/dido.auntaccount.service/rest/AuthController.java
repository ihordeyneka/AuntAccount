package dido.auntaccount.service.rest;

import dido.auntaccount.dido.auntaccount.utils.PropertiesHandler;
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

    private static final String FACEBOOK_CLIENT_ID = PropertiesHandler.getProperty("fb.client.id");
    private static final String GOOGLE_CLIENT_ID =  PropertiesHandler.getProperty("google.client.id");

    @GET
    @Path("/fb")
    public Response authFacebook() throws URISyntaxException, OAuthSystemException {

        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(OAuthProviderType.FACEBOOK.getAuthzEndpoint())
                .setClientId(FACEBOOK_CLIENT_ID)
                .setRedirectURI("http://192.168.1.111:8080/api/service/token/client/fb")
                .buildQueryMessage();
        return Response.seeOther(URI.create(request.getLocationUri())).build();
    }

    @GET
    @Path("/google")
    public Response authGoogle() throws URISyntaxException, OAuthSystemException {

        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(OAuthProviderType.GOOGLE.getAuthzEndpoint())
                .setClientId(GOOGLE_CLIENT_ID)
                .setRedirectURI("http://localhost:8080/api/service/token/client/google")
                .setResponseType("code")
                .setScope("https://www.googleapis.com/auth/userinfo.email")
                .buildQueryMessage();
        return Response.seeOther(URI.create(request.getLocationUri())).build();
    }

}
