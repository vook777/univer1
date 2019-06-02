package com.foxminded.univer.service;

import java.util.List;

import com.foxminded.univer.dao.impl.FacultyDao;
import com.foxminded.univer.dao.impl.GroupDao;
import com.foxminded.univer.dao.impl.StudentDao;
import com.foxminded.univer.models.Group;
import com.foxminded.univer.models.Student;

public class StudentService {

	private StudentDao studentDao = new StudentDao();
	private GroupDao groupDao = new GroupDao();
	private FacultyDao facultyDao = new FacultyDao(); 

	public Student save(Integer id, String firstName, String lastName, Integer groupId, String studentCardNumber) throws ClassNotFoundException {
		Student studentToSave = new Student();
		Group group = groupDao.findById(groupId).get();
		group.setFaculty(facultyDao.findById(groupDao.getFacultyId(group.getId())).get());
		studentToSave.setId(id);
		studentToSave.setFirstName(firstName);
		studentToSave.setLastName(lastName);
		studentToSave.setGroup(group);
		studentToSave.setStudentCardNumber(studentCardNumber);
		Student studentToReturn = studentDao.save(studentToSave);
		studentToReturn.setGroup(group);
		return studentToReturn;
	}
	
	public void delete(Student student) throws ClassNotFoundException {
		studentDao.delete(student);
	}
	
	public Student findById(Integer studentId) throws ClassNotFoundException {
		Student studentToReturn = studentDao.findById(studentId).get();
		Group group = groupDao.findById(studentDao.getGroupId(studentId)).get();
		group.setFaculty(facultyDao.findById(groupDao.getFacultyId(group.getId())).get());
		studentToReturn.setGroup(group);
		return studentToReturn;
	}

	public List<Student> findAll() throws ClassNotFoundException {
		List<Student> students = studentDao.findAll();
		students.stream().forEach(student -> {
			try {
				Group group = groupDao.findById(studentDao.getGroupId(student.getId())).get();
				group.setFaculty(facultyDao.findById(groupDao.getFacultyId(group.getId())).get());
				student.setGroup(group);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		return students;
	}
}
