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
import com.foxminded.univer.dao.JndiDao;
import com.foxminded.univer.models.Teacher;

public class TeacherDao extends JndiDao implements Dao<Teacher> {

    private static final Logger log = LogManager.getLogger(TeacherDao.class);

    @Override
    public Teacher save(Teacher teacher) throws ClassNotFoundException {
        Teacher teacherToReturn = null;
        if (teacher.getId() == null) {
            teacherToReturn = create(teacher);
        } else {
            teacherToReturn = update(teacher);
        }
        return teacherToReturn;
    }

    private Teacher create(Teacher teacher) throws ClassNotFoundException {
        log.trace("Entered create() method");
        String query = "insert into teachers (faculty_id, first_name, last_name) VALUES (?, ?, ?)";
        log.trace("Openinging connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, teacher.getFaculty().getId());
            statement.setString(2, teacher.getFirstName());
            statement.setString(3, teacher.getLastName());
            log.debug("Executing PreparedStatement", statement);
            statement.execute();
            log.trace("Getting result set");
            try (ResultSet resultSet = statement.getGeneratedKeys();) {
                if (resultSet.next()) {
                    log.trace("Create teacher to return");
                    teacher = findById(resultSet.getInt("id")).get();
                    log.info("Created " + teacher);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot create teacher", e);
            throw new DaoException("Cannot create teacher");
        }
        return teacher;
    }

    private Teacher update(Teacher teacher) throws ClassNotFoundException {
        log.trace("Entered update() method");
        String query = "update teachers set faculty_id = ?, first_name = ?, last_name = ? where id = ?";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teacher.getFaculty().getId());
            statement.setString(2, teacher.getFirstName());
            statement.setString(3, teacher.getLastName());
            statement.setInt(4, teacher.getId());
            log.debug("Executing PreparedStatement", statement);
            statement.execute();
            teacher = findById(teacher.getId()).get();
        } catch (SQLException e) {
            log.error("Cannot update teacher", e);
            throw new DaoException("Cannot update teacher");
        }
        return teacher;
    }

    @Override
    public void delete(Teacher teacher) throws ClassNotFoundException {
        Integer teacherId = teacher.getId();
        log.info("Deleting teacher ID = " + teacherId);
        String query = "delete from teachers where id = ?";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teacher.getId());
            log.debug("Executing PreparedStatement", statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot delete teacher", e);
            throw new DaoException("Cannot delete teacher");
        }
    }

    @Override
    public Optional<Teacher> findById(Integer id) throws ClassNotFoundException {
        log.trace("Entered findById() method");
        String query = "select * from teachers where id = ?";
        Optional<Teacher> result = Optional.empty();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            log.debug("Executing PreparedStatement, getting result set", statement);
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    log.trace("Create teacher to return");
                    result = Optional.of(extractTeacherFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find teacher", e);
            throw new DaoException("Cannot find teacher");
        }
        return result;
    }

    @Override
    public List<Teacher> findAll() throws ClassNotFoundException {
        log.trace("Entered findAll() method");
        String query = "select * from teachers";
        List<Teacher> allTeachers = new ArrayList<>();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            log.debug("Executing PreparedStatement, getting result set", statement);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    log.trace("Create list of teachers to return");
                    Teacher teacher = extractTeacherFromResultSet(resultSet);
                    allTeachers.add(teacher);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find all teachers", e);
            throw new DaoException("Cannot find all teachers");
        }
        return allTeachers;
    }
    
    public Integer getFacultyId(Integer teacherId) throws ClassNotFoundException {
		String query = "select faculty_id from teachers where id = ?";
		Integer result = null;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, teacherId);
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

    private Teacher extractTeacherFromResultSet(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt("id"));
        teacher.setFirstName(resultSet.getString("first_name"));
        teacher.setLastName(resultSet.getString("last_name"));
        return teacher;
    }
}
