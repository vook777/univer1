package com.foxminded.univer.dao;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertiesHolder {

    private static final Logger log = LogManager.getLogger(PropertiesHolder.class);
    private static String driver;
    private static String url;
    private static String user;
    private static String password;

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
        driver = properties.getProperty("DRIVER");
        url = properties.getProperty("URL");
        user = properties.getProperty("USER");
        password = properties.getProperty("PASSWORD");
        log.info("Properties set");
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
