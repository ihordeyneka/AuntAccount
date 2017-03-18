package dido.auntaccount.service.rest;

import dido.auntaccount.dido.auntaccount.utils.PropertiesHandler;
import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.mappers.JsonMapper;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import javax.inject.Inject;

public class FacebookProvider implements ClientProvider {

    public static final String REDIRECT_URI = "http://192.168.1.111:8080/api/service/token/client/fb";
    public static final String USER_INFO_URI = "https://graph.facebook.com/me?fields=id,first_name,last_name,email";
    private static final String FACEBOOK_CLIENT_ID = PropertiesHandler.getProperty("fb.client.id");
    private static final String FACEBOOK_CLIENT_SECRET = PropertiesHandler.getProperty("fb.client.secret");

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
    public UserDTO mapUser(String jsonUser) {
        return mapper.jsonFacebookToUserDTO(jsonUser);
    }

}
