package org.example.config;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigurationProperties {

    private static Properties properties;
    private static String file = "";

    public static Properties createProperties(String fileName) throws FileNotFoundException {
        if (properties == null || !file.equals(fileName)) {
            file = fileName;
            properties = new Properties();
            try (InputStream fis = ConfigurationProperties.class.getClassLoader().getResourceAsStream(fileName)) {
                properties.load(fis);
            } catch (IOException e) {
                throw new FileNotFoundException("The file " + fileName + " is missing");
            }
        }
        return properties;
    }
}