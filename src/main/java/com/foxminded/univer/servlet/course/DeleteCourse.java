package com.foxminded.univer.servlet.course;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.CourseDao;
import com.foxminded.univer.models.Course;

@WebServlet("/deleteCourse")
public class DeleteCourse extends HttpServlet {

	private CourseDao courseDao = new CourseDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Integer id = Integer.parseInt(req.getParameter("courseId"));
		try {
			Course courseToDelete = courseDao.findById(id).get();
			courseDao.delete(courseToDelete);
			req.setAttribute("deletedCourse", courseToDelete);
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/course/showDeleted.jsp").forward(req, resp);
	}
}
