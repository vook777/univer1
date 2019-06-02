package com.foxminded.univer.servlet.student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.service.StudentService;
import com.foxminded.univer.models.Student;

@WebServlet("/saveStudent")
public class SaveStudent extends HttpServlet {

	private StudentService studentService = new StudentService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Integer id = null;
		if (!req.getParameter("id").contentEquals("")) {
			id = Integer.parseInt(req.getParameter("id"));
		}
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		Integer groupId = Integer.parseInt(req.getParameter("groupId"));
		String studentCardNumber = req.getParameter("studentCardNumber");
		try {
			req.setAttribute("student", studentService.save(id, firstName, lastName, groupId, studentCardNumber));
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/student/showSaved.jsp").forward(req, resp);
	}
}
