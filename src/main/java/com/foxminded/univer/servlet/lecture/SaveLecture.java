package com.foxminded.univer.servlet.lecture;

import java.io.IOException;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.service.AuditoriumService;
import com.foxminded.univer.service.CourseService;
import com.foxminded.univer.service.GroupService;
import com.foxminded.univer.service.LectureService;
import com.foxminded.univer.service.TeacherService;
import com.foxminded.univer.models.Lecture;

@WebServlet("/saveLecture")
public class SaveLecture extends HttpServlet {

	private LectureService lectureService = new LectureService();
	private AuditoriumService auditoriumService = new AuditoriumService();
	private CourseService courseService = new CourseService();
	private GroupService groupService = new GroupService();
	private TeacherService teacherService = new TeacherService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Lecture lectureToSave = new Lecture();
		if (req.getParameter("id").contentEquals("")) {
			lectureToSave.setId(null);
		} else {
			lectureToSave.setId(Integer.parseInt(req.getParameter("id")));
		}
		try {
			lectureToSave.setAuditorium(auditoriumService.findById(Integer.parseInt(req.getParameter("auditoriumId"))));
			lectureToSave.setCourse(courseService.findById(Integer.parseInt(req.getParameter("courseId"))));
			lectureToSave.setGroup(groupService.findById(Integer.parseInt(req.getParameter("groupId"))));
			lectureToSave.setTeacher(teacherService.findById(Integer.parseInt(req.getParameter("teacherId"))));
			lectureToSave.setTime(LocalTime.parse(req.getParameter("time")));
			Lecture savedLecture = lectureService.save(lectureToSave);
			req.setAttribute("lecture", savedLecture);
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/lecture/showSaved.jsp").forward(req, resp);
	}
}
