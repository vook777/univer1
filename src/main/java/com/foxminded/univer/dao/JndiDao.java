package com.foxminded.univer.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class JndiDao {
	
	private static final Logger log = LogManager.getLogger(JdbcDao.class);

	public Connection getConnection() {
		log.trace("Entered getConnection() method");
		Connection connection = null;
		try {
			log.trace("Getting connection");
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/UsersDB");
			connection = ds.getConnection();
			log.debug("Created " + connection);
		} catch (Exception e) {
			log.error("Cannot create connection", e);
			throw new DaoException("Cannot create connection");
		}
		return connection;
	}
}
