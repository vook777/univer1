package com.foxminded.univer.servlet.lecture;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.AuditoriumDao;
import com.foxminded.univer.dao.impl.CourseDao;
import com.foxminded.univer.dao.impl.GroupDao;
import com.foxminded.univer.dao.impl.TeacherDao;

@WebServlet("/saveLectureForm")
public class SaveLectureForm extends HttpServlet {

	private AuditoriumDao auditoriumDao = new AuditoriumDao();
	private CourseDao courseDao = new CourseDao();
	private GroupDao groupDao = new GroupDao();
	private TeacherDao teacherDao = new TeacherDao();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setAttribute("auditoriums", auditoriumDao.findAll());
			req.setAttribute("courses", courseDao.findAll());
			req.setAttribute("groups", groupDao.findAll());
			req.setAttribute("teachers", teacherDao.findAll());
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/lecture/saveLecture.jsp").forward(req, resp);
	}
}
