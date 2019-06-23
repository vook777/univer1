package com.foxminded.univer.servlet.teacher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.service.TeacherService;
import com.foxminded.univer.models.Teacher;

@WebServlet("/deleteTeacher")
public class DeleteTeacher extends HttpServlet {

	private TeacherService teacherService = new TeacherService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Integer id = Integer.parseInt(req.getParameter("teacherId"));
		try {
			Teacher teacherToDelete = teacherService.findById(id);
			teacherService.delete(teacherToDelete);
			req.setAttribute("deletedTeacher", teacherToDelete);
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/teacher/showDeleted.jsp").forward(req, resp);
	}
}