package com.foxminded.univer.servlet.course;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.CourseDao;

@WebServlet("/findCourse")
public class FindCourse extends HttpServlet {

    private CourseDao courseDao = new CourseDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	Integer id = Integer.parseInt(req.getParameter("courseId"));
    	try {
            req.setAttribute("course", courseDao.findById(id).get());
        } catch (ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        req.getRequestDispatcher("/course/showCourse.jsp").forward(req, resp);
    }
}
