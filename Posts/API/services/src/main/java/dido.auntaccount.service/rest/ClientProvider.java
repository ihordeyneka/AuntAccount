package dido.auntaccount.service.rest;

import dido.auntaccount.dto.UserDTO;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public interface ClientProvider {

    OAuthProviderType getProvider();

    GrantType getGrantType();

    String getClientId();

    String getClientSecret();

    String getRedirectURI();

    String getUserInfoURI();

    UserDTO mapUser(String jsonUser);

}
