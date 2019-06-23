package com.foxminded.univer.servlet.lecture;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.service.AuditoriumService;
import com.foxminded.univer.service.CourseService;
import com.foxminded.univer.service.GroupService;
import com.foxminded.univer.service.TeacherService;

@WebServlet("/saveLectureForm")
public class SaveLectureForm extends HttpServlet {

	private AuditoriumService auditoriumService = new AuditoriumService();
	private CourseService courseService = new CourseService();
	private GroupService groupService = new GroupService();
	private TeacherService teacherService = new TeacherService();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setAttribute("auditoriums", auditoriumService.findAll());
			req.setAttribute("courses", courseService.findAll());
			req.setAttribute("groups", groupService.findAll());
			req.setAttribute("teachers", teacherService.findAll());
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/lecture/saveLecture.jsp").forward(req, resp);
	}
}
