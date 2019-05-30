package com.foxminded.univer.service;

import java.util.List;

import com.foxminded.univer.dao.impl.FacultyDao;
import com.foxminded.univer.dao.impl.GroupDao;
import com.foxminded.univer.models.Faculty;
import com.foxminded.univer.models.Group;

public class GroupService {

	private GroupDao groupDao = new GroupDao();
	private FacultyDao facultyDao = new FacultyDao();
	
	public Group findById(Integer groupId) throws ClassNotFoundException {
		Group groupToReturn = groupDao.findById(groupId).get();
		groupToReturn.setFaculty(facultyDao.findById(groupDao.getFacultyId(groupId)).get());
		return groupToReturn;
	}
	
	public void delete(Group group) throws ClassNotFoundException {
		groupDao.delete(group);
	}
	
	public List<Group> findAllGroups() throws ClassNotFoundException {
		List<Group> groups = groupDao.findAll();
		groups.stream().forEach(group -> {
			try {
				group.setFaculty(facultyDao.findById(groupDao.getFacultyId(group.getId())).get());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		return groups;
	}
	
	public Group save(Integer id, String name, Integer facultyId) throws ClassNotFoundException {
		Group groupToSave = new Group();
		Faculty faculty = facultyDao.findById(facultyId).get();
		groupToSave.setId(id);
		groupToSave.setName(name);
		groupToSave.setFaculty(faculty);
		Group groupToReturn = groupDao.save(groupToSave);
		groupToReturn.setFaculty(faculty);
		return groupToReturn;
	}
}
