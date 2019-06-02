package com.foxminded.univer.service;

import java.util.List;

import com.foxminded.univer.dao.impl.FacultyDao;
import com.foxminded.univer.models.Faculty;

public class FacultyService {

	private FacultyDao facultyDao = new FacultyDao();

	public List<Faculty> findAll() throws ClassNotFoundException {
		return facultyDao.findAll();
	}
}
