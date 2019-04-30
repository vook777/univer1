
package com.foxminded.univer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionFactory {

    private static final Logger log = LogManager.getLogger(ConnectionFactory.class);
    private PropertiesHolder propertiesHolder = new PropertiesHolder();

    public Connection getConnection() {
        log.trace("Entered getConnection() method");
        Connection connection = null;
        try {
            log.trace("Getting connection");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbunit", 
                    "postgres",
                    "123tester123");
            log.debug("Created " + connection);
        } catch (SQLException e) {
            log.error("Cannot create connection", e);
            throw new DaoException("Cannot create connection");
        }
        return connection;
    }
}
