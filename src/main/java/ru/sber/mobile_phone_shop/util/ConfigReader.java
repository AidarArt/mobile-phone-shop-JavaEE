package ru.sber.mobile_phone_shop.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс для чтения и получения записей из .properties файла
 */
public class ConfigReader {
    private static final String FILE_NAME = "application.properties";
    private static final Properties PROPERTIES = new Properties();
    private static final Logger log = LogManager.getLogger(ConfigReader.class);

    private ConfigReader() {}

    static {
        try (InputStream inp = ConfigReader.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            PROPERTIES.load(inp);
        } catch (IOException e) {
            log.log(Level.ERROR, e.getMessage());
        }
    }

    /**
     * Метод для получения записи по ее ключу
     * @param property ключ записи
     * @return запись из файла .properties
     */
    public static String getProperty(String property) {
        return PROPERTIES.getProperty(property);
    }

}
