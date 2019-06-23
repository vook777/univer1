package com.foxminded.univer.dao;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertiesHolder {

    private static final Logger log = LogManager.getLogger(PropertiesHolder.class);
    private static final String PROPERTIES = "connection.properties";
    
    public static String URL;
    public static String USER;
    public static String PASSWORD;
    public static String DRIVER;
    
    private PropertiesHolder() {}

    static {
        log.trace("Entered static block of PropertiesHolder class");
        Properties properties = new Properties();
        try {
            log.debug("Loading properties from file");
            properties.load(PropertiesHolder.class.getClassLoader().getResourceAsStream(PROPERTIES));
            log.info("Loaded " + properties);
        } catch (Exception e) {
            log.error("Cannot load properties", e);
            throw new DaoException("Cannot load properties");
        }
        log.debug("Setting properties");
        URL = properties.getProperty("URL");
        USER = properties.getProperty("USER");
        PASSWORD = properties.getProperty("PASSWORD");
        DRIVER = properties.getProperty("DRIVER");
        log.info("Properties set");
    }
}
