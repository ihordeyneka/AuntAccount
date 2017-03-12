package dido.auntaccount.service.rest;

import dido.auntaccount.dido.auntaccount.utils.OAuthRequestWrapper;
import dido.auntaccount.dto.UserDTO;
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
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.Map;

@Path("/token")
public class TokenController extends Controller {

    private static final Long EXPIRES_IN = 3600L;

    @Inject
    UserService userService;

    @Inject
    PasswordService passwordService;

    @Inject
    TokenService tokenService;

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorize(MultivaluedMap<String, String> form, @Context HttpServletRequest request) throws URISyntaxException, OAuthSystemException {

        OAuthTokenRequest oauthRequest = null;

        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

        try {
            oauthRequest = new OAuthTokenRequest(new OAuthRequestWrapper(request, form));

            UserDTO userDTO = validateClient(oauthRequest);

            // some code
            String accessToken = oauthIssuerImpl.accessToken();
            String refreshToken = oauthIssuerImpl.refreshToken();

            tokenService.saveToken(accessToken, EXPIRES_IN);

            return getResponseBuilder().header("access-token", accessToken)
                    .header("expiry", EXPIRES_IN.toString()).entity(userDTO).build();

            //if something goes wrong
        } catch (OAuthProblemException ex) {

            OAuthResponse r = OAuthResponse
                    .errorResponse(401)
                    .error(ex)
                    .buildJSONMessage();

            return getResponseBuilder().status(r.getResponseStatus()).entity(r.getBody()).build();
        }

    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("refresh")
    public Response refreshToken(MultivaluedMap<String, String> form, @Context HttpServletRequest request) throws URISyntaxException, OAuthSystemException {
        return getResponseBuilder().build();
    }

    private UserDTO validateClient(OAuthTokenRequest request) throws OAuthProblemException {
        UserDTO user = userService.findByEmail(request.getUsername());
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
