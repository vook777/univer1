package com.foxminded.univer.servlet.student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.StudentDao;
import com.foxminded.univer.models.Student;

@WebServlet("/saveStudent")
public class SaveStudent extends HttpServlet {

	private StudentDao studentDao = new StudentDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Student studentToSave = new Student();
		if (req.getParameter("id").contentEquals("")) {
			studentToSave.setId(null);
		} else {
			studentToSave.setId(Integer.parseInt(req.getParameter("id")));
		}
		studentToSave.setFirstName(req.getParameter("firstName"));
		studentToSave.setGroupId(Integer.parseInt(req.getParameter("groupId")));
		studentToSave.setLastName(req.getParameter("lastName"));
		studentToSave.setStudentCardNumber(req.getParameter("studentCardNumber"));
		try {
			Student savedStudent = studentDao.save(studentToSave);
			req.setAttribute("student", savedStudent);
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/student/showSaved.jsp").forward(req, resp);
	}
}
