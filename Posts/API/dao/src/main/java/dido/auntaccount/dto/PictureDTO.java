package dido.auntaccount.dto;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;

public abstract class PictureDTO {

    private static final Logger logger = LogManager.getLogger(PictureDTO.class);

    protected String encodeImage(byte[] image) {
        if (image == null) {
            return null;
        }
        String contentType = null;
        try {
            contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(image));
        } catch (IOException e) {
            logger.error("Can't get extension of picture");
        }
        return "data:image/" + contentType + ";base64," + Base64.encodeBase64String(image);
    }

    protected byte[] decodeImage(String image) {
        return image != null ? Base64.decodeBase64(image) : null;
    }

}
