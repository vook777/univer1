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
import com.foxminded.univer.models.Student;

public class StudentDao extends JdbcDao implements Dao<Student> {

    private static final Logger log = LogManager.getLogger(StudentDao.class);

    @Override
    public Student save(Student student) throws ClassNotFoundException {
        Student studentToReturn = null;
        if (student.getId() == null) {
            studentToReturn = create(student);
        } else {
            studentToReturn = update(student);
        }
        return studentToReturn;
    }

    private Student create(Student student) throws ClassNotFoundException {
        log.trace("Entered create() method");
        String query = "insert into students (student_card_number, firstname, lastname, group_id) VALUES (?, ?, ?, ?)";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getStudentCardNumber());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setInt(4, student.getGroupId());
            log.debug("Executing PreparedStatement");
            statement.execute();
            log.trace("Getting result set");
            try (ResultSet resultSet = statement.getGeneratedKeys();) {
                if (resultSet.next()) {
                    log.trace("Create student to return");
                    student = findById(resultSet.getInt("id")).get();
                    log.info("Created " + student);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot create student", e);
            throw new DaoException("Cannot create student");
        }
        return student;
    }

    private Student update(Student student) throws ClassNotFoundException {
        log.trace("Entered update() method");
        String query = "update students set student_card_number = ?, firstname = ?, lastname = ?, group_id = ? where id = ?";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, student.getStudentCardNumber());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setInt(4, student.getGroupId());
            statement.setInt(5, student.getId());
            log.debug("Executing PreparedStatement");
            statement.execute();
            student = findById(student.getId()).get();
        } catch (SQLException e) {
            log.error("Cannot update student", e);
            throw new DaoException("Cannot update student");
        }
        return student;
    }

    @Override
    public void delete(Student student) throws ClassNotFoundException {
        Integer studentId = student.getId();
        log.info("Deleting student ID = " + studentId);
        String query = "delete from students where id = ?";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, student.getId());
            log.debug("Executing PreparedStatement");
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Cannot delete student", e);
            throw new DaoException("Cannot delete student");
        }
    }

    @Override
    public Optional<Student> findById(Integer id) throws ClassNotFoundException {
        log.trace("Entered findById() method");
        String query = "select * from students where id = ?";
        Optional<Student> result = Optional.empty();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            log.debug("Executing PreparedStatement, getting result set");
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    log.trace("Create student to return");
                    result = Optional.of(extractStudentFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find student", e);
            throw new DaoException("Cannot find student");
        }
        return result;
    }

    public List<Student> findByGroupId(Integer groupId) throws ClassNotFoundException {
        log.trace("Entered findByGroupId() method");
        String query = "select * from students where group_id = ?";
        List<Student> studentsToReturn = new ArrayList<>();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, groupId);
            log.debug("Executing PreparedStatement, getting result set");
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    log.trace("Create list of students to return");
                    Student student = extractStudentFromResultSet(resultSet);
                    studentsToReturn.add(student);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find students by group ID", e);
            throw new DaoException("Cannot find students by group ID");
        }
        return studentsToReturn;
    }

    @Override
    public List<Student> findAll() throws ClassNotFoundException {
        log.trace("Entered findAll() method");
        String query = "select * from students";
        List<Student> allStudents = new ArrayList<>();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            log.debug("Executing PreparedStatement, getting result set");
            try (ResultSet resultSet = statement.executeQuery();) {
                log.trace("Create list of students to return");
                while (resultSet.next()) {
                    Student student = extractStudentFromResultSet(resultSet);
                    allStudents.add(student);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find all students", e);
            throw new DaoException("Cannot find all students");
        }
        return allStudents;
    }

    private Student extractStudentFromResultSet(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setStudentCardNumber(resultSet.getString("student_card_number"));
        student.setFirstName(resultSet.getString("firstname"));
        student.setLastName(resultSet.getString("lastname"));
        student.setGroupId(resultSet.getInt("group_id"));
        return student;
    }
}
