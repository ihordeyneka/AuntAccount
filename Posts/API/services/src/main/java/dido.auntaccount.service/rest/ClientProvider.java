package dido.auntaccount.service.rest;

import dido.auntaccount.dto.UserDTO;
import dido.auntaccount.mappers.JsonMapper;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public interface ClientProvider {

    OAuthProviderType getProvider();

    GrantType getGrantType();

    String getClientId();

    String getClientSecret();

    String getRedirectURI();

    String getUserInfoURI();

    UserDTO mapUser(JsonMapper mapper, String jsonUser);

    OAuthAccessTokenResponse getAccessTokenResponse(OAuthClient oAuthClient, OAuthClientRequest request) throws OAuthProblemException, OAuthSystemException;

}
