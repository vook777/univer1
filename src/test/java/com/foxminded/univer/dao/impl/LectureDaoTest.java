package com.foxminded.univer.dao.impl;

import java.io.InputStream;
import java.time.LocalTime;

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
import com.foxminded.univer.models.Auditorium;
import com.foxminded.univer.models.Course;
import com.foxminded.univer.models.Group;
import com.foxminded.univer.models.Lecture;
import com.foxminded.univer.models.Teacher;

public class LectureDaoTest extends DBTestCase {

    private LectureDao lectureDao = new LectureDao();
    private CourseDao courseDao = new CourseDao();
    private AuditoriumDao auditoriumDao = new AuditoriumDao();
    private TeacherDao teacherDao = new TeacherDao();
    private GroupDao groupDao = new GroupDao();

    public LectureDaoTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, PropertiesHolder.DRIVER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, PropertiesHolder.URL);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, PropertiesHolder.USER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PropertiesHolder.PASSWORD);
    }

    @Test
    public void testSaveNewLecture() throws Exception {
        // Given
        String[] toIgnore = { "id" };
        ITable expectedTable = getExpectedTable("addLectureTestTable.xml");
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

        // When
        lectureDao.save(lecture);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, toIgnore);
    }

    @Test
    public void testSaveExistingLecture() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("updateLectureTestTable.xml");
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

        // When
        lectureDao.save(lecture);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testDelete() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("deleteLectureTestTable.xml");
        Lecture lecture = new Lecture();
        lecture.setId(1);

        // When
        lectureDao.delete(lecture);
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindAll() throws Exception {
        // Given
        ITable expectedTable = getExpectedTable("findAllLectureTestTable.xml");

        // When
        ITable actualTable = getActualTable();

        // Then
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testFindById() throws ClassNotFoundException {
        // Given
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

        // When
        Lecture actualLecture = lectureDao.findById(1).get();

        // Then
        Assert.assertEquals(lecture, actualLecture);
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

    private ITable getExpectedTable(String fileName) throws Exception {
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
}
