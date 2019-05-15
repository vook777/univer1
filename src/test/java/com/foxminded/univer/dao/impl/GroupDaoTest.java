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
import com.foxminded.univer.models.Group;

public class GroupDaoTest extends DBTestCase {

    private GroupDao groupDao = new GroupDao();

    public GroupDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, PropertiesHolder.DRIVER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, PropertiesHolder.URL);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, PropertiesHolder.USER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PropertiesHolder.PASSWORD);
    }

    @Test
    public void testSaveNewGroup() throws Exception {
        // Given
        String[] toIgnore = { "id" };
        ITable expectedTable = getExpectedTable("addGroupTestTable.xml");
        Group group = new Group();
        group.setName("G1");
        group.setFacultyId(4);

        // When
        groupDao.save(group);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, toIgnore);
    }

    @Test
    public void testSaveExistingGroup() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("updateGroupTestTable.xml");
        Group group = new Group();
        group.setId(1);
        group.setName("P1");
        group.setFacultyId(1);

        // When
        groupDao.save(group);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testDelete() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("deleteGroupTestTable.xml");
        Group group = new Group();
        group.setId(1);

        // When
        groupDao.delete(group);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("findAllGroupTestTable.xml");

        // When
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindById() throws ClassNotFoundException {
        // Given
        Group expectedGroup = new Group();
        expectedGroup.setId(1);
        expectedGroup.setName("M1");
        expectedGroup.setFacultyId(1);

        // When
        Group actualGroup = groupDao.findById(1).get();

        // Then
        Assert.assertEquals(expectedGroup, actualGroup);
    }

    protected IDataSet getDataSet() throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream("inputGroupTestTable.xml");
        return new FlatXmlDataSetBuilder().build(inputFile);
    }

    private ITable getActualTable() throws Exception {
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("GROUPS");
        return actualTable;
    }

    private ITable getExpectedTable(String fileName) throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream(fileName);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
        ITable expectedTable = expectedDataSet.getTable("GROUPS");
        return expectedTable;
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }
}
