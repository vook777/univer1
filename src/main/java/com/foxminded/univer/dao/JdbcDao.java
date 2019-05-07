package com.foxminded.univer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class JdbcDao {

    private static final Logger log = LogManager.getLogger(JdbcDao.class);
    private PropertiesHolder propertiesHolder = new PropertiesHolder();

    public Connection getConnection() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        log.trace("Entered getConnection() method");
        Connection connection = null;
        try {
            log.trace("Getting connection");
            connection = DriverManager.getConnection(propertiesHolder.getUrl(), propertiesHolder.getUser(),
                    propertiesHolder.getPassword());
            log.debug("Created " + connection);
        } catch (SQLException e) {
            log.error("Cannot create connection", e);
            throw new DaoException("Cannot create connection");
        }
        return connection;
    }
}
