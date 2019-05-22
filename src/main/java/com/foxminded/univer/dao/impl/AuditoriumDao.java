package com.foxminded.univer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.foxminded.univer.dao.Dao;
import com.foxminded.univer.dao.DaoException;
import com.foxminded.univer.dao.JdbcDao;
import com.foxminded.univer.models.Auditorium;

public class AuditoriumDao extends JdbcDao implements Dao<Auditorium> {

	private static final Logger log = LogManager.getLogger(AuditoriumDao.class);

	@Override
	public Auditorium save(Auditorium auditorium) throws ClassNotFoundException {
		Auditorium auditoriumToReturn = null;
		if (auditorium.getId() == null) {
			auditoriumToReturn = create(auditorium);
		} else {
			auditoriumToReturn = update(auditorium);
		}
		return auditoriumToReturn;
	}

	private Auditorium create(Auditorium auditorium) throws ClassNotFoundException {
		log.trace("Entered create() method");
		String query = "insert into auditoriums (name, capacity) VALUES (?, ?)";
		log.trace("Openinging connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, auditorium.getName());
			statement.setInt(2, auditorium.getCapacity());
			log.debug("Executing PreparedStatement", statement);
			statement.execute();
			log.trace("Getting result set");
			try (ResultSet resultSet = statement.getGeneratedKeys();) {
				if (resultSet.next()) {
					log.trace("Create auditorium to return");
					auditorium = findById(resultSet.getInt("id")).get();
					log.info("Created " + auditorium);
				}
			}
		} catch (SQLException e) {
			log.error("Cannot create auditorium", e);
			throw new DaoException("Cannot create auditorium");
		}
		return auditorium;
	}

	private Auditorium update(Auditorium auditorium) throws ClassNotFoundException {
		log.trace("Entered update() method");
		String query = "update auditoriums set name = ?, capacity = ? where id = ?";
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, auditorium.getName());
			statement.setInt(2, auditorium.getCapacity());
			statement.setInt(3, auditorium.getId());
			log.debug("Executing PreparedStatement", statement);
			statement.execute();
			auditorium = findById(auditorium.getId()).get();
		} catch (SQLException e) {
			log.error("Cannot update auditorium", e);
			throw new DaoException("Cannot update auditorium");
		}
		return auditorium;
	}

	@Override
	public void delete(Auditorium auditorium) throws ClassNotFoundException {
		Integer auditoriumId = auditorium.getId();
		log.info("Deleting auditorium ID = " + auditoriumId);
		String query = "delete from auditoriums where id = ?";
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, auditoriumId);
			log.debug("Executing PreparedStatement", statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Cannot delete auditorium", e);
			throw new DaoException("Cannot delete auditorium");
		}
	}

	@Override
	public Optional<Auditorium> findById(Integer id) throws ClassNotFoundException {
		log.trace("Entered findById() method");
		String query = "select * from auditoriums where id = ?";
		Optional<Auditorium> result = Optional.empty();
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			log.debug("Executing PreparedStatement, getting result set", statement);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					log.trace("Create auditorium to return");
					result = Optional.of(extractAuditoriumFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			log.error("Cannot find auditorium", e);
			throw new DaoException("Cannot find auditorium");
		}
		return result;
	}

	@Override
	public List<Auditorium> findAll() throws ClassNotFoundException {
		log.trace("Entered findAll() method");
		String query = "select * from auditoriums";
		List<Auditorium> allAuditoriums = new ArrayList<>();
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			log.debug("Executing PreparedStatement, getting result set", statement);
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					log.trace("Create list of auditoriums to return");
					Auditorium auditorium = extractAuditoriumFromResultSet(resultSet);
					allAuditoriums.add(auditorium);
				}
			}
		} catch (SQLException e) {
			log.error("Cannot find all auditoriums", e);
			throw new DaoException("Cannot find all auditoriums");
		}
		return allAuditoriums;
	}

	private Auditorium extractAuditoriumFromResultSet(ResultSet resultSet) throws SQLException {
		Auditorium auditorium = new Auditorium();
		auditorium.setId(resultSet.getInt("id"));
		auditorium.setName(resultSet.getString("name"));
		auditorium.setCapacity(resultSet.getInt("capacity"));
		return auditorium;
	}
}
