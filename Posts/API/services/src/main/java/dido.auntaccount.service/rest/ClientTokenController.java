package dido.auntaccount.service.rest;

import dido.auntaccount.service.business.TokenService;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

@Path("/token/client")
public class ClientTokenController {

    private static final String FACEBOOK_CLIENT_ID = "";
    private static final String FACEBOOK_CLIENT_SECRET = "";

    private static final String GOOGLE_CLIENT_ID = "";
    private static final String GOOGLE_CLIENT_SECRET = "";

    private static final Long EXPIRES_IN = 3600L;

    @Inject
    TokenService tokenService;

    @GET
    @Path("/fb")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(@Context HttpServletRequest httpRequest) throws URISyntaxException, OAuthSystemException, OAuthProblemException {

        OAuthClientRequest request = OAuthClientRequest
                .tokenProvider(OAuthProviderType.FACEBOOK)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(FACEBOOK_CLIENT_ID)
                .setClientSecret(FACEBOOK_CLIENT_SECRET)
                .setRedirectURI("http://localhost:8080/api/service/token/client/fb")
                .setCode(httpRequest.getParameter("code"))
                .buildQueryMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);

        String accessToken = oAuthResponse.getAccessToken();

        OAuthResponse r = OAuthASResponse
                .tokenResponse(HttpServletResponse.SC_OK)
                .setAccessToken(accessToken)
                .setExpiresIn(EXPIRES_IN.toString())
                .buildJSONMessage();

        tokenService.saveToken(accessToken, EXPIRES_IN);

        return Response.status(r.getResponseStatus()).entity(r.getBody()).build();
    }

    @GET
    @Path("/google")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorize(@Context HttpServletRequest httpRequest) throws URISyntaxException, OAuthSystemException, OAuthProblemException {

        OAuthClientRequest request = OAuthClientRequest
                .tokenProvider(OAuthProviderType.GOOGLE)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(GOOGLE_CLIENT_ID)
                .setClientSecret(GOOGLE_CLIENT_SECRET)
                .setRedirectURI("http://localhost:8080/api/service/token/client/google")
                .setCode(httpRequest.getParameter("code"))
                .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);

        String accessToken = oAuthResponse.getAccessToken();
        Long expiresIn = oAuthResponse.getExpiresIn();

        OAuthResponse r = OAuthASResponse
                .tokenResponse(HttpServletResponse.SC_OK)
                .setAccessToken(accessToken)
                .setExpiresIn(expiresIn.toString())
                .buildJSONMessage();

        tokenService.saveToken(accessToken, EXPIRES_IN);

        return Response.status(r.getResponseStatus()).entity(r.getBody()).build();
    }

}
