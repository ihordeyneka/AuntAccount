package dido.auntaccount.service.rest;

import dido.auntaccount.dido.auntaccount.utils.PropertiesHandler;
import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.dto.UserProfileDTO;
import dido.auntaccount.mappers.JsonMapper;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class GoogleProvider implements ClientProvider {

    public static final String REDIRECT_URI = "http://localhost:8282/authcode?provider=google";
    public static final String USER_INFO_URI = "https://www.googleapis.com/oauth2/v2/userinfo";
    private static final String GOOGLE_CLIENT_ID = PropertiesHandler.getProperty("google.client.id");
    private static final String GOOGLE_CLIENT_SECRET = PropertiesHandler.getProperty("google.client.secret");

    @Override
    public OAuthProviderType getProvider() {
        return OAuthProviderType.GOOGLE;
    }

    @Override
    public GrantType getGrantType() {
        return GrantType.AUTHORIZATION_CODE;
    }

    @Override
    public String getClientId() {
        return GOOGLE_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return GOOGLE_CLIENT_SECRET;
    }

    @Override
    public String getRedirectURI() {
        return REDIRECT_URI;
    }

    @Override
    public String getUserInfoURI() {
        return USER_INFO_URI;
    }

    @Override
    public UserProfileDTO mapUser(JsonMapper mapper, String jsonUser) {
        return mapper.jsonGoogleToUserDTO(jsonUser);
    }

    @Override
    public OAuthAccessTokenResponse getAccessTokenResponse(OAuthClient oAuthClient, OAuthClientRequest request) throws OAuthProblemException, OAuthSystemException {
        return oAuthClient.accessToken(request);
    }

}
