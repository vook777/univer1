package com.foxminded.univer.dao.ddl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.foxminded.univer.dao.DaoException;
import com.foxminded.univer.dao.JdbcDao;

public class CreateTableDDL extends JdbcDao {

    public void createAuditoriumsTable() {
        String query = "create table auditoriums (" + "id serial primary key," + "name varchar (200)" + "capacity int"
                + ")";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (Exception e) {
            throw new DaoException("Cannot create table");
        }
    }

    public void createCoursesTable() {
        String query = "create table courses (" + "id serial primary key," + "name varchar (200)" + "numberofweeks int"
                + "description text" + ")";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (Exception e) {
            throw new DaoException("Cannot create table");
        }
    }

    public void createFacultiesTable() {
        String query = "create table faculties (" + "id serial primary key," + "name varchar (200)" + ")";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (Exception e) {
            throw new DaoException("Cannot create table");
        }
    }

    public void createGroupsTable() {
        String query = "create table groups (" + "id serial primary key," + "name varchar (200)" + "faculty_id int"
                + ")";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (Exception e) {
            throw new DaoException("Cannot create table");
        }
    }

    public void createLecturesTable() {
        String query = "create table lectures (" + "id serial primary key," + "course_id int" + "auditorium_id int"
                + "teacher_id int" + "group_id int" + "time time" + ")";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (Exception e) {
            throw new DaoException("Cannot create table");
        }
    }

    public void createStudentsTable() {
        String query = "create table students (" + "id serial primary key," + "studentcardnumber varchar (200)"
                + "firstname varchar (200)" + "lastname varchar (200)" + "group_id int" + ")";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (Exception e) {
            throw new DaoException("Cannot create table");
        }
    }

    public void createTeachersTable() {
        String query = "create table teachers (" + "id serial primary key," + "firstname varchar (200)"
                + "lastname varchar (200)" + "faculty_id int" + ")";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (Exception e) {
            throw new DaoException("Cannot create table");
        }
    }
}