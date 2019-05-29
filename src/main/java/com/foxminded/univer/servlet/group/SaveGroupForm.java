package com.foxminded.univer.servlet.group;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.FacultyDao;

@WebServlet("/saveGroupForm")
public class SaveGroupForm extends HttpServlet {

	private FacultyDao facultyDao = new FacultyDao();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setAttribute("faculties", facultyDao.findAll());
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/group/saveGroup.jsp").forward(req, resp);
	}
}
