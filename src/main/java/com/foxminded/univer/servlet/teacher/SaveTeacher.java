package com.foxminded.univer.servlet.teacher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.TeacherDao;
import com.foxminded.univer.models.Teacher;

@WebServlet("/saveTeacher")
public class SaveTeacher extends HttpServlet {

	private TeacherDao teacherDao = new TeacherDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Teacher teacherToSave = new Teacher();
		if (req.getParameter("id").contentEquals("")) {
			teacherToSave.setId(null);
		} else {
			teacherToSave.setId(Integer.parseInt(req.getParameter("id")));
		}
		teacherToSave.setFirstName(req.getParameter("firstName"));
		teacherToSave.setFacultyId(Integer.parseInt(req.getParameter("facultyId")));
		teacherToSave.setLastName(req.getParameter("lastName"));
		try {
			Teacher savedTeacher = teacherDao.save(teacherToSave);
			req.setAttribute("teacher", savedTeacher);
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/teacher/showSaved.jsp").forward(req, resp);
	}
}
