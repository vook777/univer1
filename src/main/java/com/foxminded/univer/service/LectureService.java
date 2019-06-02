package com.foxminded.univer.service;

import java.util.List;

import com.foxminded.univer.dao.DaoException;
import com.foxminded.univer.dao.impl.AuditoriumDao;
import com.foxminded.univer.dao.impl.CourseDao;
import com.foxminded.univer.dao.impl.GroupDao;
import com.foxminded.univer.dao.impl.LectureDao;
import com.foxminded.univer.dao.impl.TeacherDao;
import com.foxminded.univer.models.Auditorium;
import com.foxminded.univer.models.Course;
import com.foxminded.univer.models.Group;
import com.foxminded.univer.models.Lecture;
import com.foxminded.univer.models.Teacher;

public class LectureService {

	private LectureDao lectureDao = new LectureDao();
	private AuditoriumDao auditoriumDao = new AuditoriumDao();
	private CourseDao courseDao = new CourseDao();
	private GroupDao groupDao = new GroupDao();
	private TeacherDao teacherDao = new TeacherDao();

	public Lecture save(Lecture lecture) throws ClassNotFoundException {
		Auditorium auditorium = lecture.getAuditorium();
		Course course = lecture.getCourse();
		Group group = lecture.getGroup();
		Teacher teacher = lecture.getTeacher();
		Lecture lectureToReturn = lectureDao.save(lecture);
		lectureToReturn.setAuditorium(auditorium);
		lectureToReturn.setCourse(course);
		lectureToReturn.setGroup(group);
		lectureToReturn.setTeacher(teacher);
		return lectureToReturn;
	}
	
	public void delete(Lecture lecture) throws ClassNotFoundException {
		lectureDao.delete(lecture);
	}

	public Lecture findById(Integer lectureId) throws DaoException, ClassNotFoundException {
		Lecture lectureToReturn = lectureDao.findById(lectureId).get();
		lectureToReturn.setAuditorium(auditoriumDao.findById(lectureDao.getAuditoriumId(lectureId)).get());
		lectureToReturn.setCourse(courseDao.findById(lectureDao.getCourseId(lectureId)).get());
		lectureToReturn.setGroup(groupDao.findById(lectureDao.getGroupId(lectureId)).get());
		lectureToReturn.setTeacher(teacherDao.findById(lectureDao.getTeacherId(lectureId)).get());
		return lectureToReturn;
	}
	
	public List<Lecture> findAll() throws DaoException, ClassNotFoundException {
		List<Lecture> lectures = lectureDao.findAll();
		for(Lecture lecture : lectures) {
			Integer lectureId = lecture.getId();
			lecture.setAuditorium(auditoriumDao.findById(lectureDao.getAuditoriumId(lectureId)).get());
			lecture.setCourse(courseDao.findById(lectureDao.getCourseId(lectureId)).get());
			lecture.setGroup(groupDao.findById(lectureDao.getGroupId(lectureId)).get());
			lecture.setTeacher(teacherDao.findById(lectureDao.getTeacherId(lectureId)).get());
		}
		return lectures;
	}
}
