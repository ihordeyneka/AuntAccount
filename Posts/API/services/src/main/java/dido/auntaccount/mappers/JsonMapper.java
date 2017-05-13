package dido.auntaccount.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dido.auntaccount.dto.SellerDTO;
import dido.auntaccount.dto.UserDTO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class JsonMapper {

    private static final Logger logger = LogManager.getLogger(JsonMapper.class);

    private ObjectMapper mapper = new ObjectMapper();

    public SellerDTO jsonToSellerDTO(byte[] json) {
        try {
            return mapper.readValue(json, SellerDTO.class);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Couldn't get seller from json", e);
        }
        return null;
    }

    public byte[] sellerDTOToJson(SellerDTO sellerDTO) {
        try {
            return mapper.writeValueAsBytes(sellerDTO);
        } catch (JsonProcessingException e) {
            logger.log(Level.ERROR, "Couldn't get json from seller", e);
        }
        return null;
    }

    public UserDTO jsonFacebookToUserDTO(String json) {
        UserDTO user = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            user = new UserDTO();
            user.setClientId((String) jsonObject.get("id"));
            user.setEmail((String) jsonObject.get("email"));
            user.setFirstName((String) jsonObject.get("first_name"));
            user.setLastName((String) jsonObject.get("last_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public UserDTO jsonGoogleToUserDTO(String json) {
        UserDTO user = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            user = new UserDTO();
            user.setClientId((String) jsonObject.get("id"));
            user.setEmail((String) jsonObject.get("email"));
            user.setFirstName((String) jsonObject.get("given_name"));
            user.setLastName((String) jsonObject.get("family_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

}
