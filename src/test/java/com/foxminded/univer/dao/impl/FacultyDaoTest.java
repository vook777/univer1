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
import com.foxminded.univer.models.Faculty;

public class FacultyDaoTest extends DBTestCase {

    private PropertiesHolder propertiesHolder = new PropertiesHolder();
    private FacultyDao facultyDao = new FacultyDao();

    public FacultyDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, propertiesHolder.getDriver());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, propertiesHolder.getUrl());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, propertiesHolder.getUser());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, propertiesHolder.getPassword());
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

    private ITable getExpextedTable(String fileName) throws Exception {
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

    @Test
    public void testSaveNewFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Geography");
        facultyDao.save(faculty);
        String[] toIgnore = { "id" };

        ITable actualTable = getActualTable();

        ITable expectedTable = getExpextedTable("addFacultyTestTable.xml");

        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());
        Assertion.assertEqualsIgnoreCols(expectedTable, filteredActualTable, toIgnore);
    }

    @Test
    public void testSaveExistingFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1);
        faculty.setName("Physics");
        facultyDao.save(faculty);

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("updateFacultyTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testDelete() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1);
        facultyDao.delete(faculty);

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("deleteFacultyTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Faculty> facultysList = facultyDao.findAll();

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("findAllFacultyTestTable.xml");

        Assertion.assertEquals(expectedTable, actualTable);
        Assert.assertEquals(expectedTable.getRowCount(), facultysList.size());
    }

    @Test
    public void testFindById() throws ClassNotFoundException {
        Faculty expectedFaculty = new Faculty();
        expectedFaculty.setId(1);
        expectedFaculty.setName("Maths");

        Faculty actualFaculty = facultyDao.findById(1).get();

        Assert.assertEquals(expectedFaculty, actualFaculty);
    }
}
