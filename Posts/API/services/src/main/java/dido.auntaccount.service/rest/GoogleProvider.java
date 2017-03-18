package dido.auntaccount.service.rest;

import dido.auntaccount.dido.auntaccount.utils.PropertiesHandler;
import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.mappers.JsonMapper;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import javax.inject.Inject;

public class GoogleProvider implements ClientProvider {

    public static final String REDIRECT_URI = "http://localhost:8080/api/service/token/client/google";
    public static final String USER_INFO_URI = "https://www.googleapis.com/oauth2/v2/userinfo";
    private static final String GOOGLE_CLIENT_ID = PropertiesHandler.getProperty("google.client.id");
    private static final String GOOGLE_CLIENT_SECRET = PropertiesHandler.getProperty("google.client.secret");

    @Inject
    JsonMapper mapper;

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
    public UserDTO mapUser(String jsonUser) {
        return mapper.jsonGoogleToUserDTO(jsonUser);
    }
}
