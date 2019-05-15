package com.foxminded.univer.dao.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Test;

import com.foxminded.univer.dao.PropertiesHolder;
import com.foxminded.univer.models.Student;

public class StudentDaoTest extends DBTestCase {

    private StudentDao studentDao = new StudentDao();

    public StudentDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, PropertiesHolder.DRIVER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, PropertiesHolder.URL);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, PropertiesHolder.USER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PropertiesHolder.PASSWORD);
    }

    @Test
    public void testSaveNewStudent() throws Exception {
        // Given
        String[] toIgnore = { "id" };
        ITable expectedTable = getExpectedTable("addStudentTestTable.xml");
        Student student = new Student();
        student.setStudentCardNumber("ef67");
        student.setFirstName("Lee");
        student.setLastName("Sin");
        student.setGroupId(2);

        // When
        studentDao.save(student);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, toIgnore);
    }

    @Test
    public void testSaveExistingStudent() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("updateStudentTestTable.xml");
        Student student = new Student();
        student.setId(1);
        student.setFirstName("Max");
        student.setLastName("Pain");
        student.setStudentCardNumber("ab23");
        student.setGroupId(2);

        // When
        studentDao.save(student);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testDelete() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("deleteStudentTestTable.xml");
        Student student = new Student();
        student.setId(1);
        student.setFirstName("Max");
        student.setLastName("Pain");
        student.setStudentCardNumber("ab23");
        student.setGroupId(3);

        // When
        studentDao.delete(student);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("findAllStudentTestTable.xml");

        // When
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindById() throws ClassNotFoundException {
        // Given
        Student expectedStudent = new Student();
        expectedStudent.setId(2);
        expectedStudent.setFirstName("Peter");
        expectedStudent.setLastName("Pan");
        expectedStudent.setStudentCardNumber("bc34");
        expectedStudent.setGroupId(3);

        // When
        Student actualStudent = studentDao.findById(2).get();

        // Then
        Assert.assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void testFindByGroupId() throws Exception {
        // Given
        List<Student> expectedList = new ArrayList<>();
        expectedList.add(studentDao.findById(1).get());
        expectedList.add(studentDao.findById(2).get());
        expectedList.add(studentDao.findById(3).get());

        // When
        List<Student> studentsList = studentDao.findByGroupId(3);

        // Then
        Assert.assertEquals(expectedList, studentsList);
    }

    protected IDataSet getDataSet() throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream("inputStudentTestTable.xml");
        return new FlatXmlDataSetBuilder().build(inputFile);
    }

    private ITable getActualTable() throws Exception {
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("STUDENTS");
        return actualTable;
    }

    private ITable getExpectedTable(String fileName) throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream(fileName);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
        ITable expectedTable = expectedDataSet.getTable("STUDENTS");
        return expectedTable;
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }
}
