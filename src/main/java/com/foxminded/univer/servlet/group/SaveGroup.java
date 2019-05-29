package com.foxminded.univer.servlet.group;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.FacultyDao;
import com.foxminded.univer.dao.impl.GroupDao;
import com.foxminded.univer.models.Group;

@WebServlet("/saveGroup")
public class SaveGroup extends HttpServlet {

	private GroupDao groupDao = new GroupDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Group groupToSave = new Group();
		if (req.getParameter("id").contentEquals("")) {
			groupToSave.setId(null);
		} else {
			groupToSave.setId(Integer.parseInt(req.getParameter("id")));
		}
		groupToSave.setName(req.getParameter("name"));
		groupToSave.setFacultyId(Integer.parseInt(req.getParameter("facultyId")));
		try {
			Group savedGroup = groupDao.save(groupToSave);
			req.setAttribute("group", savedGroup);
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/group/showSaved.jsp").forward(req, resp);
	}
}
