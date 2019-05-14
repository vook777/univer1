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
import com.foxminded.univer.models.Course;

public class CourseDao extends JdbcDao implements Dao<Course> {

    private static final Logger log = LogManager.getLogger(CourseDao.class);

    @Override
    public Course save(Course course) throws ClassNotFoundException {
        Course courseToReturn = null;
        if (course.getId() == null) {
            courseToReturn = create(course);
        } else {
            courseToReturn = update(course);
        }
        return courseToReturn;
    }

    private Course create(Course course) throws ClassNotFoundException {
        log.trace("Entered create() method");
        String query = "insert into courses (name, number_of_weeks, description) VALUES (?, ?, ?)";
        log.trace("Openinging connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, course.getName());
            statement.setInt(2, course.getNumberOfWeeks());
            statement.setString(3, course.getDescription());
            log.debug("Executing PreparedStatement", statement);
            statement.execute();
            log.trace("Getting result set");
            try (ResultSet resultSet = statement.getGeneratedKeys();) {
                if (resultSet.next()) {
                    log.trace("Create course to return");
                    course = findById(resultSet.getInt("id")).get();
                    log.info("Created " + course);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot create course", e);
            throw new DaoException("Cannot create course");
        }
        return course;
    }

    private Course update(Course course) throws ClassNotFoundException {
        log.trace("Entered update() method");
        String query = "update courses set name = ?, number_of_weeks = ?, description = ? where id = ?";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, course.getName());
            statement.setInt(2, course.getNumberOfWeeks());
            statement.setString(3, course.getDescription());
            statement.setInt(4, course.getId());
            log.debug("Executing PreparedStatement", statement);
            statement.execute();
            course = findById(course.getId()).get();
        } catch (SQLException e) {
            log.error("Cannot update course", e);
            throw new DaoException("Cannot update course");
        }
        return course;
    }

    @Override
    public void delete(Course course) throws ClassNotFoundException {
        Integer courseId = course.getId();
        log.info("Deleting course ID = " + courseId);
        String query = "delete from courses where id = ?";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, course.getId());
            log.debug("Executing PreparedStatement", statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot delete course", e);
            throw new DaoException("Cannot delete course");
        }
    }

    @Override
    public Optional<Course> findById(Integer id) throws ClassNotFoundException {
        log.trace("Entered findById() method");
        String query = "select * from courses where id = ?";
        Optional<Course> result = Optional.empty();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            log.debug("Executing PreparedStatement, getting result set", statement);
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    log.trace("Create course to return");
                    result = Optional.of(extractCourseFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find course", e);
            throw new DaoException("Cannot find course");
        }
        return result;
    }

    @Override
    public List<Course> findAll() throws ClassNotFoundException {
        log.trace("Entered findAll() method");
        String query = "select * from courses";
        List<Course> allCourses = new ArrayList<>();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            log.debug("Executing PreparedStatement, getting result set", statement);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    log.trace("Create list of courses to return");
                    Course course = extractCourseFromResultSet(resultSet);
                    allCourses.add(course);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find all courses", e);
            throw new DaoException("Cannot find all courses");
        }
        return allCourses;
    }

    private Course extractCourseFromResultSet(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("id"));
        course.setName(resultSet.getString("name"));
        course.setNumberOfWeeks(resultSet.getInt("number_of_weeks"));
        course.setDescription(resultSet.getString("description"));
        return course;
    }
}
