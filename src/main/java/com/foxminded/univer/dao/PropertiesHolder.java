package com.foxminded.univer.dao;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertiesHolder {

    private static final Logger log = LogManager.getLogger(PropertiesHolder.class);
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER;

    static {
        log.trace("Entered static block of PropertiesHolder class");
        Properties properties = new Properties();
        try {
            log.debug("Loading properties from file");
            properties.load(PropertiesHolder.class.getClassLoader().getResourceAsStream("connection.properties"));
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

    public String getUrl() {
        return URL;
    }

    public String getUser() {
        return USER;
    }

    public String getPassword() {
        return PASSWORD;
    }
    
    public String getDriver() {
        return DRIVER;
    }
}
