package com.foxminded.univer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.foxminded.univer.dao.ConnectionFactory;
import com.foxminded.univer.dao.DaoException;
import com.foxminded.univer.dao.StudentDao;
import com.foxminded.univer.models.Student;

public class StudentDaoImpl implements StudentDao {

    private static final Logger log = LogManager.getLogger(StudentDaoImpl.class);
    private ConnectionFactory connectionFactory = new ConnectionFactory();

    @Override
    public Student create(Student student) {
        log.trace("Entered create() method");
        String query = "insert into students (studentcardnumber, firstname, lastname, group_id) VALUES (?, ?, ?, ?)";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = connectionFactory.getConnection();
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
                    student = extractStudentFromResultSet(resultSet);
                    log.info("Created " + student);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot create student", e);
            throw new DaoException("Cannot create student");
        }
        return student;
    }

    @Override
    public Student update(Student student) {
        log.trace("Entered update() method");
        String query = "update students set studentcardnumber = ?, firstname = ?, lastname = ?, group_id = ? where id = ?";
        log.trace("Opening connection, preparing statement");
        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, student.getStudentCardNumber());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setInt(4, student.getGroupId());
            statement.setInt(5, student.getId());
            log.debug("Executing PreparedStatement, getting result set");
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    log.trace("Create student to return");
                    student = extractStudentFromResultSet(resultSet);
                    log.trace("Student with ID " + student.getId() + " updated.");
                }
            }
        } catch (SQLException e) {
            log.error("Cannot update student", e);
            throw new DaoException("Cannot update student");
        }
        return student;
    }

    @Override
    public boolean delete(Student student) {
        Integer studentId = student.getId();
        log.info("Deleting student ID = " + studentId);
        String query = "delete from students where id = ?";
        boolean result = false;
        log.trace("Opening connection, preparing statement");
        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, student.getId());
            log.debug("Executing PreparedStatement");
            int i = statement.executeUpdate();
            if (i == 1) {
                result = true;
                log.info("Student ID = " + studentId + " has been deleted.");
            }
        } catch (SQLException e) {
            log.error("Cannot delete student", e);
            throw new DaoException("Cannot delete student");
        }
        return result;
    }

    @Override
    public Student findById(Integer id) {
        log.trace("Entered findById() method");
        String query = "select * from students where id = ?";
        Student studentToReturn = new Student();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            log.debug("Executing PreparedStatement, getting result set");
            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    log.trace("Create student to return");
                    studentToReturn = extractStudentFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot find student", e);
            throw new DaoException("Cannot find student");
        }
        return studentToReturn;
    }

    @Override
    public List<Student> findByGroupId(Integer groupId) {
        log.trace("Entered findByGroupId() method");
        String query = "select * from students where group_id = ?";
        List<Student> studentsToReturn = new ArrayList<>();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = connectionFactory.getConnection();
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
    public List<Student> findAll() {
        log.trace("Entered findAll() method");
        String query = "select * from students";
        List<Student> allStudents = new ArrayList<>();
        log.trace("Opening connection, preparing statement");
        try (Connection connection = connectionFactory.getConnection();
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
        student.setStudentCardNumber(resultSet.getString("studentcardnumber"));
        student.setFirstName(resultSet.getString("firstname"));
        student.setLastName(resultSet.getString("lastname"));
        student.setGroupId(resultSet.getInt("group_id"));
        return student;
    }
}
