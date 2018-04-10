package dido.auntaccount.service.filter;

import dido.auntaccount.dto.TokenDTO;
import dido.auntaccount.service.business.TokenService;
import dido.auntaccount.service.business.UserService;
import dido.auntaccount.service.rest.Tokens;
import dido.auntaccount.service.rest.controller.TokenController;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.joda.time.DateTime;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Date;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    public static final String LOGGED_IN_USER = "loggedInUser";
    public static final String ANONYMOUS_TOKEN_PREFIX = "Anonymous";

    @Context
    private HttpServletRequest request;

    @Inject
    private TokenService tokenService;

    @Inject
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.HEADER);

            String accessToken = oauthRequest.getAccessToken();

            accessToken = handleAnonymousUser(accessToken);

            TokenDTO tokenDTO = validateToken(accessToken);
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
            headers.add(TokenController.ACCESS_TOKEN, tokenDTO.getToken());
            headers.add(LOGGED_IN_USER, tokenDTO.getUserId().toString());

        } catch (OAuthProblemException | OAuthSystemException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private String handleAnonymousUser(String accessToken) throws OAuthSystemException {
        boolean anonymousUser = accessToken.startsWith(ANONYMOUS_TOKEN_PREFIX);
        if (anonymousUser) {
            Long userId = userService.saveAnonymousUser();
            return tokenService.issueNativeTokens(userId).getAccessToken();
        }
        return accessToken;
    }

    private TokenDTO validateToken(String token) throws OAuthProblemException {
        TokenDTO serverToken = tokenService.getToken(token);
        Date now = DateTime.now().toDate();
        if (serverToken == null || serverToken.getExpirationDate().before(now)) {
            throw OAuthProblemException.error("Token is not valid");
        }
        return serverToken;
    }

}