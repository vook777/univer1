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
import com.foxminded.univer.models.Teacher;

public class TeacherDaoTest extends DBTestCase {

    private PropertiesHolder propertiesHolder = new PropertiesHolder();
    private TeacherDao teacherDao = new TeacherDao();

    public TeacherDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, propertiesHolder.getDriver());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, propertiesHolder.getUrl());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, propertiesHolder.getUser());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, propertiesHolder.getPassword());
    }

    protected IDataSet getDataSet() throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream("inputTeacherTestTable.xml");
        return new FlatXmlDataSetBuilder().build(inputFile);
    }

    private ITable getActualTable() throws Exception {
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("TEACHERS");
        return actualTable;
    }

    private ITable getExpextedTable(String fileName) throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream(fileName);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
        ITable expectedTable = expectedDataSet.getTable("TEACHERS");
        return expectedTable;
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Test
    public void testSaveNewTeacher() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Dan");
        teacher.setLastName("Drake");
        teacher.setFacultyId(4);
        teacherDao.save(teacher);
        String[] toIgnore = { "id" };

        ITable actualTable = getActualTable();

        ITable expectedTable = getExpextedTable("addTeacherTestTable.xml");

        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());
        Assertion.assertEqualsIgnoreCols(expectedTable, filteredActualTable, toIgnore);
    }

    @Test
    public void testSaveExistingTeacher() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setFirstName("Alf");
        teacher.setLastName("Ant");
        teacher.setFacultyId(3);
        teacherDao.save(teacher);

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("updateTeacherTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testDelete() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacherDao.delete(teacher);

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("deleteTeacherTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Teacher> teachersList = teacherDao.findAll();

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("findAllTeacherTestTable.xml");

        Assertion.assertEquals(expectedTable, actualTable);
        Assert.assertEquals(expectedTable.getRowCount(), teachersList.size());
    }

    @Test
    public void testFindById() throws ClassNotFoundException {
        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setId(1);
        expectedTeacher.setFirstName("Alf");
        expectedTeacher.setLastName("Ant");
        expectedTeacher.setFacultyId(1);

        Teacher actualTeacher = teacherDao.findById(1).get();

        Assert.assertEquals(expectedTeacher, actualTeacher);
    }
}
