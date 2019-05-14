package com.foxminded.univer.dao.impl;

import java.io.InputStream;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Test;

import com.foxminded.univer.dao.PropertiesHolder;
import com.foxminded.univer.models.Course;

public class CourseDaoTest extends DBTestCase {

    private PropertiesHolder propertiesHolder = new PropertiesHolder();
    private CourseDao courseDao = new CourseDao();

    public CourseDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, propertiesHolder.getDriver());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, propertiesHolder.getUrl());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, propertiesHolder.getUser());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, propertiesHolder.getPassword());
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

    private ITable getExpextedTable(String fileName) throws Exception {
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

    @Test
    public void testSaveNewCourse() throws Exception {
        Course course = new Course();
        course.setName("Geography");
        course.setNumberOfWeeks(19);
        course.setDescription("Geography");
        courseDao.save(course);
        String[] toIgnore = { "id" };

        ITable actualTable = getActualTable();

        ITable expectedTable = getExpextedTable("addCourseTestTable.xml");

        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());
        Assertion.assertEqualsIgnoreCols(expectedTable, filteredActualTable, toIgnore);
    }

    @Test
    public void testSaveExistingCourse() throws Exception {
        Course course = new Course();
        course.setId(1);
        course.setName("Maths");
        course.setNumberOfWeeks(100);
        course.setDescription("Maths");
        courseDao.save(course);

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("updateCourseTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testDelete() throws Exception {
        Course course = new Course();
        course.setId(1);
        courseDao.delete(course);

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("deleteCourseTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Course> coursesList = courseDao.findAll();

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("findAllCourseTestTable.xml");

        Assertion.assertEquals(expectedTable, actualTable);
        Assert.assertEquals(expectedTable.getRowCount(), coursesList.size());
    }

    @Test
    public void testFindById() throws ClassNotFoundException {
        Course expectedCourse = new Course();
        expectedCourse.setId(1);
        expectedCourse.setName("Maths");
        expectedCourse.setNumberOfWeeks(20);
        expectedCourse.setDescription("Maths");

        Course actualCourse = courseDao.findById(1).get();

        Assert.assertEquals(expectedCourse, actualCourse);
    }
}
