package dido.auntaccount.service.rest;

import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.entities.User;
import dido.auntaccount.service.business.PasswordService;
import dido.auntaccount.service.business.TokenService;
import dido.auntaccount.service.business.UserService;
import dido.auntaccount.service.business.impl.PasswordServiceImpl.CannotPerformOperationException;
import dido.auntaccount.service.business.impl.PasswordServiceImpl.InvalidHashException;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

@Path("/token")
public class TokenController {

    private static final Long EXPIRES_IN = 3600L;

    @Inject
    UserService userService;

    @Inject
    PasswordService passwordService;

    @Inject
    TokenService tokenService;

    @POST
    public Response authorize(@Context HttpServletRequest request) throws URISyntaxException, OAuthSystemException {

        OAuthTokenRequest oauthRequest = null;

        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

        try {
            oauthRequest = new OAuthTokenRequest(request);

            validateClient(oauthRequest);

            // some code
            String accessToken = oauthIssuerImpl.accessToken();
            String refreshToken = oauthIssuerImpl.refreshToken();

            // some code
            OAuthResponse r = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setExpiresIn(EXPIRES_IN.toString())
                    .setRefreshToken(refreshToken)
                    .buildJSONMessage();

            tokenService.saveToken(accessToken, EXPIRES_IN);

            return Response.status(r.getResponseStatus()).entity(r.getBody()).build();

            //if something goes wrong
        } catch (OAuthProblemException ex) {

            OAuthResponse r = OAuthResponse
                    .errorResponse(401)
                    .error(ex)
                    .buildJSONMessage();

            return Response.status(r.getResponseStatus()).entity(r.getBody()).build();
        }

    }

    private void validateClient(OAuthTokenRequest request) throws OAuthProblemException {
        UserDTO user = userService.findByUserName(request.getUsername());
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
    }

}
