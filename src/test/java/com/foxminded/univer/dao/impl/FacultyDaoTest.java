package com.foxminded.univer.dao.impl;

import java.io.InputStream;

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
import com.foxminded.univer.models.Faculty;

public class FacultyDaoTest extends DBTestCase {

    private FacultyDao facultyDao = new FacultyDao();

    public FacultyDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, PropertiesHolder.DRIVER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, PropertiesHolder.URL);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, PropertiesHolder.USER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PropertiesHolder.PASSWORD);
    }

    @Test
    public void testSaveNewFaculty() throws Exception {
        // Given
        String[] toIgnore = { "id" };
        ITable expectedTable = getExpectedTable("addFacultyTestTable.xml");
        Faculty faculty = new Faculty();
        faculty.setName("Geography");

        // When
        facultyDao.save(faculty);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, toIgnore);
    }

    @Test
    public void testSaveExistingFaculty() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("updateFacultyTestTable.xml");
        Faculty faculty = new Faculty();
        faculty.setId(1);
        faculty.setName("Physics");

        // When
        facultyDao.save(faculty);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testDelete() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("deleteFacultyTestTable.xml");
        Faculty faculty = new Faculty();
        faculty.setId(1);

        // When
        facultyDao.delete(faculty);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("findAllFacultyTestTable.xml");

        // When
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindById() throws ClassNotFoundException {
        // Given
        Faculty expectedFaculty = new Faculty();
        expectedFaculty.setId(1);
        expectedFaculty.setName("Maths");

        // When
        Faculty actualFaculty = facultyDao.findById(1).get();

        // Then
        Assert.assertEquals(expectedFaculty, actualFaculty);
    }

    protected IDataSet getDataSet() throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream("inputFacultyTestTable.xml");
        return new FlatXmlDataSetBuilder().build(inputFile);
    }

    private ITable getActualTable() throws Exception {
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("FACULTIES");
        return actualTable;
    }

    private ITable getExpectedTable(String fileName) throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream(fileName);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
        ITable expectedTable = expectedDataSet.getTable("FACULTIES");
        return expectedTable;
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }
}
