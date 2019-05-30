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
import com.foxminded.univer.models.Group;

public class GroupDao extends JdbcDao implements Dao<Group> {

	private static final Logger log = LogManager.getLogger(GroupDao.class);
	private StudentDao studentDao = new StudentDao();

	@Override
	public Group save(Group group) throws ClassNotFoundException {
		Group groupToReturn = null;
		if (group.getId() == null) {
			groupToReturn = create(group);
		} else {
			groupToReturn = update(group);
		}
		return groupToReturn;
	}

	private Group create(Group group) throws ClassNotFoundException {
		log.trace("Entered create() method");
		String query = "insert into groups (group_name, faculty_id) VALUES (?, ?)";
		log.trace("Openinging connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, group.getName());
			statement.setInt(2, group.getFaculty().getId());
			log.debug("Executing PreparedStatement", statement);
			statement.execute();
			log.trace("Getting result set");
			try (ResultSet resultSet = statement.getGeneratedKeys();) {
				if (resultSet.next()) {
					log.trace("Create group to return");
					group = findById(resultSet.getInt("id")).get();
					log.info("Created " + group);
				}
			}
		} catch (SQLException e) {
			log.error("Cannot create group", e);
			throw new DaoException("Cannot create group");
		}
		return group;
	}

	private Group update(Group group) throws ClassNotFoundException {
		log.trace("Entered update() method");
		String query = "update groups set group_name = ?, faculty_id = ? where id = ?";
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, group.getName());
			statement.setInt(2, group.getFaculty().getId());
			statement.setInt(3, group.getId());
			log.debug("Executing PreparedStatement", statement);
			statement.execute();
			group = findById(group.getId()).get();
		} catch (SQLException e) {
			log.error("Cannot update group", e);
			throw new DaoException("Cannot update group");
		}
		return group;
	}

	@Override
	public void delete(Group group) throws ClassNotFoundException {
		Integer groupId = group.getId();
		log.info("Deleting group ID = " + groupId);
		removeAllStudentsFromGroup(groupId);
		String query = "delete from groups where id = ?";
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, group.getId());
			log.debug("Executing PreparedStatement", statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Cannot delete group", e);
			throw new DaoException("Cannot delete group");
		}
	}

	@Override
	public Optional<Group> findById(Integer id) throws ClassNotFoundException {
		log.trace("Entered findById() method");
		String query = "select * from groups where id = ?";
		Optional<Group> result = Optional.empty();
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			log.debug("Executing PreparedStatement, getting result set", statement);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					log.trace("Create group to return");
					result = Optional.of(extractGroupFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			log.error("Cannot find group", e);
			throw new DaoException("Cannot find group");
		}
		return result;
	}

	public List<Group> findByFacultyId(Integer facultyId) throws ClassNotFoundException {
		log.trace("Entered findByFacultyId() method");
		String query = "select * from groups where faculty_id = ?";
		List<Group> groupsToReturn = new ArrayList<>();
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, facultyId);
			log.debug("Executing PreparedStatement, getting result set", statement);
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					log.trace("Create list of groups to return");
					Group group = extractGroupFromResultSet(resultSet);
					groupsToReturn.add(group);
				}
			}
		} catch (SQLException e) {
			log.error("Cannot find groups by faculty ID", e);
			throw new DaoException("Cannot find groups by faculty ID");
		}
		return groupsToReturn;
	}

	@Override
	public List<Group> findAll() throws ClassNotFoundException {
		log.trace("Entered findAll() method");
		String query = "select * from groups";
		List<Group> allGroups = new ArrayList<>();
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			log.debug("Executing PreparedStatement, getting result set", statement);
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					log.trace("Create list of groups to return");
					Group group = extractGroupFromResultSet(resultSet);
					allGroups.add(group);
				}
			}
		} catch (SQLException e) {
			log.error("Cannot find all groups", e);
			throw new DaoException("Cannot find all groups");
		}
		return allGroups;
	}

	public Integer getFacultyId(Integer groupId) throws ClassNotFoundException {
		String query = "select faculty_id from groups where id = ?";
		Integer result = null;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, groupId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					result = resultSet.getInt("faculty_id");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot find faculty ID");
		}
		return result;
	}

	private Group extractGroupFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
		Group group = new Group();
		group.setId(resultSet.getInt("id"));
		group.setName(resultSet.getString("group_name"));
		group.setStudents(studentDao.findByGroupId(group.getId()));
		return group;
	}

	private void removeAllStudentsFromGroup(Integer groupId) throws ClassNotFoundException {
		log.trace("Entered removeAllStudentsFromGroup() method");
		String query = "update students set group_id = null where group_id = ?";
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, groupId);
			log.debug("Executing PreparedStatement", statement);
			statement.execute();
		} catch (SQLException e) {
			log.error("Cannot remove all students from group", e);
			throw new DaoException("Cannot remove all students from group");
		}
	}
}
