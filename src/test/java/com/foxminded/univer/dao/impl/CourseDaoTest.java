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
import com.foxminded.univer.models.Course;

public class CourseDaoTest extends DBTestCase {

    private CourseDao courseDao = new CourseDao();

    public CourseDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, PropertiesHolder.DRIVER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, PropertiesHolder.URL);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, PropertiesHolder.USER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PropertiesHolder.PASSWORD);
    }

    @Test
    public void testSaveNewCourse() throws Exception {
        // Given
        String[] toIgnore = { "id" };
        ITable expectedTable = getExpectedTable("addCourseTestTable.xml");
        Course course = new Course();
        course.setName("Geography");
        course.setNumberOfWeeks(19);
        course.setDescription("Geography");

        // When
        courseDao.save(course);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, toIgnore);
    }

    @Test
    public void testSaveExistingCourse() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("updateCourseTestTable.xml");
        Course course = new Course();
        course.setId(1);
        course.setName("Maths");
        course.setNumberOfWeeks(100);
        course.setDescription("Maths");

        // When
        courseDao.save(course);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testDelete() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("deleteCourseTestTable.xml");
        Course course = new Course();
        course.setId(1);

        // When
        courseDao.delete(course);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("findAllCourseTestTable.xml");

        // When
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindById() throws ClassNotFoundException {
        // Given
        Course expectedCourse = new Course();
        expectedCourse.setId(1);
        expectedCourse.setName("Maths");
        expectedCourse.setNumberOfWeeks(20);
        expectedCourse.setDescription("Maths");

        // When
        Course actualCourse = courseDao.findById(1).get();

        // Then
        Assert.assertEquals(expectedCourse, actualCourse);
    }

    protected IDataSet getDataSet() throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream("inputCourseTestTable.xml");
        return new FlatXmlDataSetBuilder().build(inputFile);
    }

    private ITable getActualTable() throws Exception {
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("COURSES");
        return actualTable;
    }

    private ITable getExpectedTable(String fileName) throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream(fileName);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
        ITable expectedTable = expectedDataSet.getTable("COURSES");
        return expectedTable;
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }
}
