package dido.auntaccount.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dido.auntaccount.dto.SupplierDTO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class JsonMapper {

    private static final Logger logger = LogManager.getLogger(JsonMapper.class);

    private ObjectMapper mapper = new ObjectMapper();

    public SupplierDTO jsonToSupplierDTO(byte[] json) {
        try {
            return mapper.readValue(json, SupplierDTO.class);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Couldn't get supplier from json", e);
        }
        return null;
    }

    public byte[] supplierDTOToJson(SupplierDTO supplierDTO) {
        try {
            return mapper.writeValueAsBytes(supplierDTO);
        } catch (JsonProcessingException e) {
            logger.log(Level.ERROR, "Couldn't get json from supplier", e);
        }
        return null;
    }

}
