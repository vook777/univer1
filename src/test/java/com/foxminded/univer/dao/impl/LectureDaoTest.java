package com.foxminded.univer.dao.impl;

import java.io.InputStream;
import java.time.LocalTime;
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
import com.foxminded.univer.models.Course;
import com.foxminded.univer.models.Group;
import com.foxminded.univer.models.Lecture;
import com.foxminded.univer.models.Teacher;

public class LectureDaoTest extends DBTestCase {

    private PropertiesHolder propertiesHolder = new PropertiesHolder();
    private LectureDao lectureDao = new LectureDao();
    private CourseDao courseDao = new CourseDao();
    private AuditoriumDao auditoriumDao = new AuditoriumDao();
    private TeacherDao teacherDao = new TeacherDao();
    private GroupDao groupDao = new GroupDao();

    public LectureDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, propertiesHolder.getDriver());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, propertiesHolder.getUrl());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, propertiesHolder.getUser());
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, propertiesHolder.getPassword());
    }

    protected IDataSet getDataSet() throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream("inputLectureTestTable.xml");
        return new FlatXmlDataSetBuilder().build(inputFile);
    }

    private ITable getActualTable() throws Exception {
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("LECTURES");
        return actualTable;
    }

    private ITable getExpextedTable(String fileName) throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputFile = classLoader.getResourceAsStream(fileName);
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(inputFile);
        ITable expectedTable = expectedDataSet.getTable("LECTURES");
        return expectedTable;
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Test
    public void testSaveNewLecture() throws Exception {
        Lecture lecture = new Lecture();
        Course c = courseDao.findById(1).get();
        Auditorium a = auditoriumDao.findById(1).get();
        Teacher t = teacherDao.findById(1).get();
        Group g = groupDao.findById(1).get();
        LocalTime lt = LocalTime.of(12, 00, 00);
        lecture.setCourse(c);
        lecture.setAuditorium(a);
        lecture.setTeacher(t);
        lecture.setGroup(g);
        lecture.setTime(lt);
        lectureDao.save(lecture);
        String[] toIgnore = { "id" };

        ITable actualTable = getActualTable();

        ITable expectedTable = getExpextedTable("addLectureTestTable.xml");

        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());
        Assertion.assertEqualsIgnoreCols(expectedTable, filteredActualTable, toIgnore);
    }

    @Test
    public void testSaveExistingLecture() throws Exception {
        Lecture lecture = new Lecture();
        Course c = courseDao.findById(1).get();
        Auditorium a = auditoriumDao.findById(1).get();
        Teacher t = teacherDao.findById(3).get();
        Group g = groupDao.findById(1).get();
        LocalTime lt = LocalTime.of(9, 00, 00);
        lecture.setId(1);
        lecture.setCourse(c);
        lecture.setAuditorium(a);
        lecture.setTeacher(t);
        lecture.setGroup(g);
        lecture.setTime(lt);
        lectureDao.save(lecture);

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("updateLectureTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testDelete() throws Exception {
        Lecture lecture = new Lecture();
        lecture.setId(1);
        lectureDao.delete(lecture);

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("deleteLectureTestTable.xml");
        ITable filteredActualTable = DefaultColumnFilter.includedColumnsTable(actualTable,
                expectedTable.getTableMetaData().getColumns());

        Assertion.assertEquals(expectedTable, filteredActualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Lecture> lecturesList = lectureDao.findAll();

        ITable actualTable = getActualTable();
        ITable expectedTable = getExpextedTable("findAllLectureTestTable.xml");

        Assertion.assertEquals(expectedTable, actualTable);
        Assert.assertEquals(expectedTable.getRowCount(), lecturesList.size());
    }

    @Test
    public void testFindById() throws ClassNotFoundException {
        Lecture lecture = new Lecture();
        Course c = courseDao.findById(1).get();
        Auditorium a = auditoriumDao.findById(1).get();
        Teacher t = teacherDao.findById(3).get();
        Group g = groupDao.findById(1).get();
        LocalTime lt = LocalTime.of(9, 00, 00);
        lecture.setId(1);
        lecture.setCourse(c);
        lecture.setAuditorium(a);
        lecture.setTeacher(t);
        lecture.setGroup(g);
        lecture.setTime(lt);

        Lecture actualLecture = lectureDao.findById(1).get();

        Assert.assertEquals(lecture, actualLecture);
    }
}
