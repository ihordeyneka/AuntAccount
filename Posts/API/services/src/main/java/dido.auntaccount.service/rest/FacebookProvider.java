package dido.auntaccount.service.rest;

import dido.auntaccount.dido.auntaccount.utils.PropertiesHandler;
import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.dto.UserProfileDTO;
import dido.auntaccount.mappers.JsonMapper;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class FacebookProvider implements ClientProvider {

    public static final String REDIRECT_URI =  "http://192.168.1.111:8282/authcode?provider=fb";
    public static final String USER_INFO_URI = "https://graph.facebook.com/me?fields=id,first_name,last_name,email";
    private static final String FACEBOOK_CLIENT_ID = PropertiesHandler.getProperty("fb.client.id");
    private static final String FACEBOOK_CLIENT_SECRET = PropertiesHandler.getProperty("fb.client.secret");

    @Override
    public OAuthProviderType getProvider() {
        return OAuthProviderType.FACEBOOK;
    }

    @Override
    public GrantType getGrantType() {
        return GrantType.AUTHORIZATION_CODE;
    }

    @Override
    public String getClientId() {
        return FACEBOOK_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return FACEBOOK_CLIENT_SECRET;
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
        return mapper.jsonFacebookToUserDTO(jsonUser);
    }

    @Override
    public OAuthAccessTokenResponse getAccessTokenResponse(OAuthClient oAuthClient,  OAuthClientRequest request) throws OAuthProblemException, OAuthSystemException {
        return oAuthClient.accessToken(request, GitHubTokenResponse.class);
    }

}
