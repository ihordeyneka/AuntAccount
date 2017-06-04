package dido.auntaccount.service.rest.controller;

import dido.auntaccount.service.business.TokenService;
import dido.auntaccount.service.rest.FacebookProvider;
import dido.auntaccount.service.rest.GoogleProvider;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static dido.auntaccount.service.rest.controller.TokenController.ACCESS_TOKEN;

@Path("/auth")
public class AuthController extends Controller {

    private FacebookProvider facebookProvider = new FacebookProvider();
    private GoogleProvider googleProvider = new GoogleProvider();

    @Inject
    TokenService tokenService;

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

    @DELETE
    @Path("/sign_out")
    public Response signOut(@HeaderParam(ACCESS_TOKEN) String token) {
        tokenService.deleteToken(token);
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/sign_out")
    public Response signOutPreflight() {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/fb")
    public Response authFacebookPreflight() throws URISyntaxException, OAuthSystemException {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Path("/google")
    public Response authGooglePreflight() throws URISyntaxException, OAuthSystemException {
        return getResponseBuilder().build();
    }

}
