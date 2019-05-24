package com.foxminded.univer.servlet.lecture;

import java.io.IOException;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.AuditoriumDao;
import com.foxminded.univer.dao.impl.CourseDao;
import com.foxminded.univer.dao.impl.GroupDao;
import com.foxminded.univer.dao.impl.LectureDao;
import com.foxminded.univer.dao.impl.TeacherDao;
import com.foxminded.univer.models.Lecture;

@WebServlet("/saveLecture")
public class SaveLecture extends HttpServlet {

	private LectureDao lectureDao = new LectureDao();
	private AuditoriumDao auditoriumDao = new AuditoriumDao();
	private CourseDao courseDao = new CourseDao();
	private GroupDao groupDao = new GroupDao();
	private TeacherDao teacherDao = new TeacherDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Lecture lectureToSave = new Lecture();
		if (req.getParameter("id").contentEquals("")) {
			lectureToSave.setId(null);
		} else {
			lectureToSave.setId(Integer.parseInt(req.getParameter("id")));
		}
		try {
		lectureToSave.setAuditorium(auditoriumDao.findById(Integer.parseInt(req.getParameter("auditoriumId"))).get());
		lectureToSave.setCourse(courseDao.findById(Integer.parseInt(req.getParameter("courseId"))).get());
		lectureToSave.setGroup(groupDao.findById(Integer.parseInt(req.getParameter("groupId"))).get());
		lectureToSave.setTeacher(teacherDao.findById(Integer.parseInt(req.getParameter("teacherId"))).get());
		lectureToSave.setTime(LocalTime.parse(req.getParameter("time")));
			Lecture savedLecture = lectureDao.save(lectureToSave);
			req.setAttribute("lecture", savedLecture);
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/lecture/showSaved.jsp").forward(req, resp);
	}
}
