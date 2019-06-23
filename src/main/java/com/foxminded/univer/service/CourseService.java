package com.foxminded.univer.service;

import java.util.List;

import com.foxminded.univer.dao.impl.CourseDao;
import com.foxminded.univer.models.Course;

public class CourseService {

	private CourseDao courseDao = new CourseDao();
	
	public Course findById(Integer courseId) throws ClassNotFoundException {
		return courseDao.findById(courseId).get();
	}
	
	public List<Course> findAll() throws ClassNotFoundException {
		return courseDao.findAll();
	}
}
