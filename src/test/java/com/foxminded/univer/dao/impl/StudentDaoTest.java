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
import com.foxminded.univer.models.Student;

public class StudentDaoTest extends DBTestCase {

    private PropertiesHolder propertiesHolder = new PropertiesHolder();
    private StudentDao studentDao = new StudentDao();

    public StudentDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, propertiesHolder.getDriver());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                "jdbc:postgresql://localhost:5432/dbunit");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, propertiesHolder.getUser());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, propertiesHolder.getPassword());
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

    private ITable getExpextedTable(String fileName) throws Exception {
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

    @Test
    public void testSaveNewStudent() throws Exception {
        Student student = new Student();
        student.setStudentCardNumber("ef67");
        student.setFirstName("Lee");
        student.setLastName("Sin");
        student.setGroupId(2);
        studentDao.save(student);
        String[] toIgnore = {"id"};

        ITable actualTable = getActualTable();

        ITable expectedTable = getExpextedTable("addStudentTestTable.xml");

        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());
        Assertion.assertEqualsIgnoreCols(expectedTable, filteredActualTable, toIgnore);
    }

    @Test
    public void testSaveExistingStudent() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setFirstName("Max");
        student.setLastName("Pain");
        student.setStudentCardNumber("ab23");
        student.setGroupId(2);
        studentDao.save(student);

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("updateStudentTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }
    
    @Test
    public void testDelete() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setFirstName("Max");
        student.setLastName("Pain");
        student.setStudentCardNumber("ab23");
        student.setGroupId(3);
        studentDao.delete(student);
        
        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("deleteStudentTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Student> studentsList = studentDao.findAll();

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("findAllStudentTestTable.xml");

        Assertion.assertEquals(expectedTable, actualTable);
        Assert.assertEquals(expectedTable.getRowCount(), studentsList.size());
    }
    
    @Test
    public void testFindById() {
        Student expectedStudent = new Student();
        expectedStudent.setId(2);
        expectedStudent.setFirstName("Peter");
        expectedStudent.setLastName("Pan");
        expectedStudent.setStudentCardNumber("bc34");
        expectedStudent.setGroupId(3);
        
        Student actualStudent = studentDao.findById(2).get();
        
        Assert.assertEquals(expectedStudent, actualStudent);               
    }
    
    @Test
    public void testFindByGroupId() throws Exception {
        List<Student> studentsList = studentDao.findByGroupId(3);
        
        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("findAllStudentTestTable.xml");
        
        Assertion.assertEquals(expectedTable, actualTable);
        Assert.assertEquals(expectedTable.getRowCount(), studentsList.size());
    }
}