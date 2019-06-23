package com.foxminded.univer.servlet.auditorium;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.AuditoriumDao;

@WebServlet("/deleteAuditoriumForm")
public class DeleteAuditoriumForm extends HttpServlet {

private AuditoriumDao auditoriumDao = new AuditoriumDao();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setAttribute("auditoriums", auditoriumDao.findAll());
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/auditorium/deleteAuditorium.jsp").forward(req, resp);
	}
}
