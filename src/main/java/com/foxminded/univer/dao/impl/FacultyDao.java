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
import com.foxminded.univer.models.Faculty;

public class FacultyDao extends JdbcDao implements Dao<Faculty> {

    private static final Logger log = LogManager.getLogger(FacultyDao.class);
    private GroupDao groupDao = new GroupDao();

    @Override
    public Faculty save(Faculty faculty) throws ClassNotFoundException {
        Faculty facultyToReturn = null;
        if (faculty.getId() == null) {
            facultyToReturn = create(faculty);
        } else {
            facultyToReturn = update(faculty);
        }
        return facultyToReturn;
    }

    private Faculty create(Faculty faculty) throws ClassNotFoundException {
        log.trace("Entered create() method");
        String query = "insert into faculties (faculty_name) VALUES (?)";
        log.trace("Openinging connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, faculty.getName());
            log.debug("Executing PreparedStatement", statement);
            statement.execute();
            log.trace("Getting result set");
            try (ResultSet resultSet = statement.getGeneratedKeys();) {
                if (resultSet.next()) {
                    log.trace("Create faculty to return");
                    faculty = findById(resultSet.getInt("id")).get();
                    log.info("Created " + faculty);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot create faculty", e);
            throw new DaoException("Cannot create faculty");
        }
        return faculty;
    }

    private Faculty update(Faculty faculty) throws ClassNotFoundException {
        log.trace("Entered update() method");
        String query = "update faculties set faculty_name = ? where id = ?";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, faculty.getName());
            statement.setInt(2, faculty.getId());
            log.debug("Executing PreparedStatement", statement);
            statement.execute();
            faculty = findById(faculty.getId()).get();
        } catch (SQLException e) {
            log.error("Cannot update faculty", e);
            throw new DaoException("Cannot update faculty");
        }
        return faculty;
    }

    @Override
    public void delete(Faculty faculty) throws ClassNotFoundException {
        Integer facultyId = faculty.getId();
        log.info("Deleting faculty ID = " + facultyId);
        removeAllGroupsFromFaculty(facultyId);
        removeAllTeachersFromFaculty(facultyId);
        String query = "delete from faculties where id = ?";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, faculty.getId());
            log.debug("Executing PreparedStatement", statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot delete faculty", e);
            throw new DaoException("Cannot delete faculty");
        }
    }

    @Override
    public Optional<Faculty> findById(Integer id) throws ClassNotFoundException {
        log.trace("Entered findById() method");
        String query = "select * from faculties where id = ?";
        Optional<Faculty> result = Optional.empty();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            log.debug("Executing PreparedStatement, getting result set", statement);
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    log.trace("Create faculty to return");
                    result = Optional.of(extractFacultyFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find faculty", e);
            throw new DaoException("Cannot find faculty");
        }
        return result;
    }

    @Override
    public List<Faculty> findAll() throws ClassNotFoundException {
        log.trace("Entered findAll() method");
        String query = "select * from faculties";
        List<Faculty> allFacultys = new ArrayList<>();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            log.debug("Executing PreparedStatement, getting result set", statement);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    log.trace("Create list of faculties to return");
                    Faculty faculty = extractFacultyFromResultSet(resultSet);
                    allFacultys.add(faculty);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find all faculties", e);
            throw new DaoException("Cannot find all faculties");
        }
        return allFacultys;
    }

    private Faculty extractFacultyFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        Faculty faculty = new Faculty();
        faculty.setId(resultSet.getInt("id"));
        faculty.setName(resultSet.getString("faculty_name"));
        faculty.setGroups(groupDao.findByFacultyId(faculty.getId()));
        return faculty;
    }

    private void removeAllGroupsFromFaculty(Integer facultyId) throws ClassNotFoundException {
        log.trace("Entered removeAllGroupsFromFaculty() method");
        String query = "update groups set faculty_id = null where faculty_id = ?";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, facultyId);
            log.debug("Executing PreparedStatement", statement);
            statement.execute();
        } catch (SQLException e) {
            log.error("Cannot remove all groups from faculty", e);
            throw new DaoException("Cannot remove all groups from faculty");
        }
    }

    private void removeAllTeachersFromFaculty(Integer facultyId) throws ClassNotFoundException {
        log.trace("Entered removeAllTeachersFromFaculty() method");
        String query = "update teachers set faculty_id = null where faculty_id = ?";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, facultyId);
            log.debug("Executing PreparedStatement", statement);
            statement.execute();
        } catch (SQLException e) {
            log.error("Cannot remove all teachers from faculty", e);
            throw new DaoException("Cannot remove all teachers from faculty");
        }
    }
}
