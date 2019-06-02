package com.foxminded.univer.service;

import java.util.List;

import com.foxminded.univer.dao.impl.FacultyDao;
import com.foxminded.univer.dao.impl.TeacherDao;
import com.foxminded.univer.models.Faculty;
import com.foxminded.univer.models.Teacher;

public class TeacherService {

	private TeacherDao teacherDao = new TeacherDao();
	private FacultyDao facultyDao = new FacultyDao();
	
	public Teacher save(Integer id, String firstName, String lastName, Integer facultyId) throws ClassNotFoundException {
		Teacher teacherToSave = new Teacher();
		Faculty faculty = facultyDao.findById(facultyId).get();
		teacherToSave.setId(id);
		teacherToSave.setFirstName(firstName);
		teacherToSave.setLastName(lastName);
		teacherToSave.setFaculty(faculty);
		Teacher teacherToReturn = teacherDao.save(teacherToSave);
		teacherToReturn.setFaculty(faculty);
		return teacherToReturn;
	}
	
	public void delete(Teacher teacher) throws ClassNotFoundException {
		teacherDao.delete(teacher);
	}
	
	public Teacher findById(Integer teacherId) throws ClassNotFoundException {
		Teacher teacherToReturn = teacherDao.findById(teacherId).get();
		Faculty faculty = facultyDao.findById(teacherDao.getFacultyId(teacherId)).get();
		teacherToReturn.setFaculty(faculty);
		return teacherToReturn;
	}

	public List<Teacher> findAll() throws ClassNotFoundException {
		List<Teacher> teachers = teacherDao.findAll();
		teachers.stream().forEach(teacher -> {
			try {
				Faculty faculty = facultyDao.findById(teacherDao.getFacultyId(teacher.getId())).get();
				teacher.setFaculty(faculty);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		return teachers;
	}
}
