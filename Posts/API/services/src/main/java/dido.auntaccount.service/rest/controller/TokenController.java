package dido.auntaccount.service.rest.controller;

import dido.auntaccount.dido.auntaccount.utils.OAuthRequestWrapper;
import dido.auntaccount.dido.auntaccount.utils.PropertiesHandler;
import dido.auntaccount.dto.RefreshTokenDTO;
import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.dto.UserProfileDTO;
import dido.auntaccount.mappers.JsonMapper;
import dido.auntaccount.service.business.PasswordService;
import dido.auntaccount.service.business.TokenService;
import dido.auntaccount.service.business.UserService;
import dido.auntaccount.service.business.impl.PasswordServiceImpl.CannotPerformOperationException;
import dido.auntaccount.service.business.impl.PasswordServiceImpl.InvalidHashException;
import dido.auntaccount.service.rest.ClientProvider;
import dido.auntaccount.service.rest.FacebookProvider;
import dido.auntaccount.service.rest.GoogleProvider;
import dido.auntaccount.service.rest.Tokens;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.sql.Date;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/token")
public class TokenController extends Controller {

    public static final String ACCESS_TOKEN = "access-token";
    public static final String REFRESH_TOKEN = "refresh-token";
    public static final String EXPIRES_IN = "expires_in";
    public static final String FACEBOOK = "fb";
    public static final String GOOGLE = "google";
    public static final String SERVER_GRANT_TYPE = "grant_type";
    public static final String SERVER_CLIENT_SECRET = "client_secret";
    public static final String SERVER_CLIENT_ID = "client_id";
    public static final String SERVER_CLIENT_ID_VALUE = PropertiesHandler.getProperty("server.client.id");
    public static final String SERVER_CLIENT_SECRET_VALUE = PropertiesHandler.getProperty("server.client.secret");
    public static final String SERVER_GRANT_TYPE_VALUE = PropertiesHandler.getProperty("server.grant.type");

    @Inject
    PasswordService passwordService;

    @Inject
    TokenService tokenService;

    @Inject
    private UserService userService;

    @Inject
    JsonMapper mapper;

    private FacebookProvider facebookProvider = new FacebookProvider();
    private GoogleProvider googleProvider = new GoogleProvider();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/client")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(@FormParam("provider") String provider, @FormParam("code") String code) throws URISyntaxException, OAuthSystemException, OAuthProblemException {
        ClientProvider clientProvider = null;
        switch (provider) {
            case FACEBOOK:
                clientProvider = facebookProvider;
                break;
            case GOOGLE:
                clientProvider = googleProvider;
                break;
            default:
                return getResponseBuilder(BAD_REQUEST.getStatusCode()).build();
        }
        return buildResponse(clientProvider, code);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorize(MultivaluedMap<String, String> form, @Context HttpServletRequest request) throws OAuthSystemException {

        UserProfileDTO userDTO = null;
        appendOAuthRequestParams(form);

        try {
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(new OAuthRequestWrapper(request, form));
            userDTO = validateClient(oauthRequest);

        } catch (OAuthProblemException ex) {
            return getResponseBuilder(UNAUTHORIZED.getStatusCode()).entity(ex).build();
        }

        Tokens tokens = tokenService.issueNativeTokens(userDTO.getId());

        return getResponseBuilder()
                .header(ACCESS_TOKEN, tokens.getAccessToken())
                .header(REFRESH_TOKEN, tokens.getRefreshToken())
                .header(EXPIRES_IN, tokens.getAccessExpirationDate()).entity(userDTO).build();
    }

    private void appendOAuthRequestParams(MultivaluedMap<String, String> form) {
        form.add(SERVER_GRANT_TYPE, SERVER_GRANT_TYPE_VALUE);
        form.add(SERVER_CLIENT_SECRET, SERVER_CLIENT_SECRET_VALUE);
        form.add(SERVER_CLIENT_ID, SERVER_CLIENT_ID_VALUE);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("refresh")
    public Response refreshToken(@FormParam("refreshToken") String refreshToken) throws OAuthSystemException {
        RefreshTokenDTO storedRefreshToken = tokenService.getRefreshToken(refreshToken);

        if (storedRefreshToken == null || !storedRefreshToken.isValid()) {
            return Response.status(UNAUTHORIZED).build();
        }

        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

        String accessToken = oauthIssuerImpl.accessToken();

        long accessExpirationDate = tokenService.getAccessExpirationDate();
        tokenService.saveAccessToken(accessToken, accessExpirationDate, storedRefreshToken.getUserId());

        return getResponseBuilder()
                .header(ACCESS_TOKEN, accessToken)
                .header(EXPIRES_IN, accessExpirationDate).build();

    }

    @OPTIONS
    @Consumes("application/x-www-form-urlencoded")
    @Path("/client")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenPreflight(@FormParam("provider") String provider, @FormParam("code") String code) throws URISyntaxException, OAuthSystemException, OAuthProblemException {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorizePreflight(MultivaluedMap<String, String> form, @Context HttpServletRequest request) throws URISyntaxException, OAuthSystemException {
        return getResponseBuilder().build();
    }

    @OPTIONS
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("refresh")
    public Response refreshTokenPreflight(@PathParam("refreshToken") String refreshToken) throws URISyntaxException, OAuthSystemException {
        return getResponseBuilder().build();

    }

    private Response buildResponse(ClientProvider provider, String code) throws OAuthProblemException, OAuthSystemException {
        OAuthClientRequest request = OAuthClientRequest
                .tokenProvider(provider.getProvider())
                .setGrantType(provider.getGrantType())
                .setClientId(provider.getClientId())
                .setClientSecret(provider.getClientSecret())
                .setRedirectURI(provider.getRedirectURI())
                .setCode(code)
                .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        OAuthAccessTokenResponse oAuthResponse = provider.getAccessTokenResponse(oAuthClient, request);

        OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(provider.getUserInfoURI()).buildQueryMessage();
        bearerClientRequest.addHeader("Authorization", "Bearer " + oAuthResponse.getAccessToken());

        OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);

        String body = resourceResponse.getBody();
        UserProfileDTO user = provider.mapUser(mapper, body);
        user.setCreationDate(new Date(DateTime.now().getMillis()));

        UserDTO savedUser = userService.saveUser(user);
        Tokens tokens = tokenService.issueNativeTokens(savedUser.getId());

        return getResponseBuilder()
                .header(ACCESS_TOKEN, tokens.getAccessToken())
                .header(REFRESH_TOKEN, tokens.getRefreshToken())
                .header(EXPIRES_IN, tokens.getAccessExpirationDate()).entity(savedUser).build();
    }

    private UserProfileDTO validateClient(OAuthTokenRequest request) throws OAuthProblemException {
        UserProfileDTO user = userService.findByEmail(request.getUsername());
        if (user == null) {
            throw OAuthProblemException.error("User is not valid " + request.getUsername());
        }
        boolean isPasswordCorrect;
        try {
            isPasswordCorrect = passwordService.verifyPassword(request.getPassword(), user.getPassword());
        } catch (CannotPerformOperationException | InvalidHashException e) {
            throw OAuthProblemException.error("Password is not valid for user " + request.getUsername());
        }
        if (!isPasswordCorrect) {
            throw OAuthProblemException.error("Password is not valid for user " + request.getUsername());
        }
        return user;
    }



}
