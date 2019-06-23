package com.foxminded.univer.servlet.course;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.CourseDao;
import com.foxminded.univer.models.Course;

@WebServlet("/saveCourse")
public class SaveCourse extends HttpServlet {

	private CourseDao courseDao = new CourseDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Course courseToSave = new Course();
		if (req.getParameter("id").contentEquals("")) {
			courseToSave.setId(null);
		} else {
			courseToSave.setId(Integer.parseInt(req.getParameter("id")));
		}
		courseToSave.setName(req.getParameter("name"));
		courseToSave.setNumberOfWeeks(Integer.parseInt(req.getParameter("numberOfWeeks")));
		courseToSave.setDescription(req.getParameter("description"));
		try {
			Course savedCourse = courseDao.save(courseToSave);
			req.setAttribute("course", savedCourse);
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/course/showSaved.jsp").forward(req, resp);
	}
}
