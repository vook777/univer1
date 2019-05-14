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
import com.foxminded.univer.models.Auditorium;

public class AuditoriumDaoTest extends DBTestCase {

    private PropertiesHolder propertiesHolder = new PropertiesHolder();
    private AuditoriumDao auditoriumDao = new AuditoriumDao();

    public AuditoriumDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, propertiesHolder.getDriver());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                propertiesHolder.getUrl());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, propertiesHolder.getUser());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, propertiesHolder.getPassword());
    }

    protected IDataSet getDataSet() throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream("inputAuditoriumTestTable.xml");
        return new FlatXmlDataSetBuilder().build(inputFile);
    }

    private ITable getActualTable() throws Exception {
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("AUDITORIUMS");
        return actualTable;
    }

    private ITable getExpextedTable(String fileName) throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream(fileName);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
        ITable expectedTable = expectedDataSet.getTable("AUDITORIUMS");
        return expectedTable;
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Test
    public void testSaveNewAuditorium() throws Exception {
        Auditorium auditorium = new Auditorium();
        auditorium.setName("A4");
        auditorium.setCapacity(30);
        auditoriumDao.save(auditorium);
        String[] toIgnore = {"id"};

        ITable actualTable = getActualTable();

        ITable expectedTable = getExpextedTable("addAuditoriumTestTable.xml");

        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());
        Assertion.assertEqualsIgnoreCols(expectedTable, filteredActualTable, toIgnore);
    }

    @Test
    public void testSaveExistingAuditorium() throws Exception {
        Auditorium auditorium = new Auditorium();
        auditorium.setId(1);
        auditorium.setName("A1");
        auditorium.setCapacity(60);
        auditoriumDao.save(auditorium);

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("updateAuditoriumTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }
    
    @Test
    public void testDelete() throws Exception {
        Auditorium auditorium = new Auditorium();
        auditorium.setId(1);
        auditorium.setName("A1");
        auditorium.setCapacity(30);
        auditoriumDao.delete(auditorium);
        
        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("deleteAuditoriumTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Auditorium> auditoriumsList = auditoriumDao.findAll();

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("findAllAuditoriumTestTable.xml");

        Assertion.assertEquals(expectedTable, actualTable);
        Assert.assertEquals(expectedTable.getRowCount(), auditoriumsList.size());
    }
    
    @Test
    public void testFindById() throws ClassNotFoundException {
        Auditorium expectedAuditorium = new Auditorium();
        expectedAuditorium.setId(1);
        expectedAuditorium.setName("A1");
        expectedAuditorium.setCapacity(30);
        
        Auditorium actualAuditorium = auditoriumDao.findById(1).get();
        
        Assert.assertEquals(expectedAuditorium, actualAuditorium);               
    }
}
