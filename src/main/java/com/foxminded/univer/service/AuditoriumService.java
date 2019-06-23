package com.foxminded.univer.service;

import java.util.List;

import com.foxminded.univer.dao.impl.AuditoriumDao;
import com.foxminded.univer.models.Auditorium;

public class AuditoriumService {

	public AuditoriumDao auditoriumDao = new AuditoriumDao();
	
	public Auditorium findById(Integer auditoriumId) throws ClassNotFoundException {
		return auditoriumDao.findById(auditoriumId).get();
	}
	
	public List<Auditorium> findAll() throws ClassNotFoundException {
		return auditoriumDao.findAll();
	}
}
