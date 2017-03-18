package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dido.auntaccount.utils.PropertiesHandler;
import dido.auntaccount.service.rest.FacebookProvider;
import dido.auntaccount.service.rest.GoogleProvider;
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

    private static final String GOOGLE_CLIENT_ID =  PropertiesHandler.getProperty("google.client.id");
    private FacebookProvider facebookProvider = new FacebookProvider();
    private GoogleProvider googleProvider = new GoogleProvider();

    @GET
    @Path("/fb")
    public Response authFacebook() throws URISyntaxException, OAuthSystemException {

        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(OAuthProviderType.FACEBOOK.getAuthzEndpoint())
                .setClientId(facebookProvider.getClientId())
                .setRedirectURI(facebookProvider.getRedirectURI())
                .setScope("email")
                .buildQueryMessage();
        return Response.seeOther(URI.create(request.getLocationUri())).build();
    }

    @GET
    @Path("/google")
    public Response authGoogle() throws URISyntaxException, OAuthSystemException {

        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(OAuthProviderType.GOOGLE.getAuthzEndpoint())
                .setClientId(googleProvider.getClientId())
                .setRedirectURI(googleProvider.getRedirectURI())
                .setResponseType("code")
                .setScope("https://www.googleapis.com/auth/userinfo.email")
                .setParameter("access_type", "offline")
                .buildQueryMessage();
        return Response.seeOther(URI.create(request.getLocationUri())).build();
    }

}
