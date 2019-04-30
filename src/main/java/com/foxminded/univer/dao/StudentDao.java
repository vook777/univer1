package com.foxminded.univer.dao;

import java.util.List;

import com.foxminded.univer.models.Student;

public interface StudentDao extends CrudDao<Student> {

    List<Student> findByGroupId(Integer groupId);
}
