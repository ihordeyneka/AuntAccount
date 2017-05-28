package dido.auntaccount.dido.auntaccount.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class PropertiesHandler {

    private static final Logger logger = LogManager.getLogger(PropertiesHandler.class);

    private static final String PROPERTIES_FILE = "config.properties";
    private static Properties properties = new Properties();

    public static synchronized Properties initProperties() {

        try {

            InputStream inputStream = PropertiesHandler.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);

            if (inputStream ==null) {
                throw new IllegalStateException("Properties file not found " + PROPERTIES_FILE);
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new IllegalStateException("Cannot load system properties", e);
        }
        return properties;

        /*
        String currentDir = System.getProperty("user.dir");
         try {


            File propertiesFile = Paths.get(currentDir).resolve(PROPERTIES_FILE).toFile();

            InputStream inputStream;
            if (propertiesFile.exists()) {
                inputStream = new FileInputStream(propertiesFile);
                logger.info("Found external properties file " + propertiesFile.getAbsolutePath());
            } else {
                logger.info("Using default properties file, no property file found in " + currentDir);
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                inputStream = classLoader.getResourceAsStream(PROPERTIES_FILE);
                if (inputStream == null)
                    throw new FileNotFoundException("Properties file does not exist");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new IllegalStateException("Cannot load system properties", e);
        }*/

        }

    public static synchronized String getProperty(String key) {
        if (properties.isEmpty()) {
            initProperties();
        }
        if (!properties.containsKey(key)) {
            logger.error("Property doesn't exist with key " + key);
        }
        return properties.getProperty(key);
    }
}
