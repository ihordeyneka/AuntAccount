package dido.auntaccount.service.filter;

import dido.auntaccount.dao.TokenDAO;
import dido.auntaccount.entities.Token;
import dido.auntaccount.service.business.TokenService;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Date;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest request;

    @Inject
    private TokenService tokenService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
      /*  try {
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.HEADER);

            String accessToken = oauthRequest.getAccessToken();

            validateToken(accessToken);

        } catch (OAuthProblemException | OAuthSystemException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }*/
    }

    private void validateToken(String token) throws OAuthProblemException {
        Token serverToken = tokenService.getToken(token);
        Date now = DateTime.now().toDate();
        if (serverToken == null || serverToken.getExpirationDate().before(now)) {
            throw OAuthProblemException.error("Token is not valid");
        }
    }

}