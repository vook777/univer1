package com.foxminded.univer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.CourseDao;

@WebServlet("/courses")
public class GetCourses extends HttpServlet {

    private CourseDao courseDao = new CourseDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("courses", courseDao.findAll());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/showAllCourses.jsp").forward(req, resp);
    }
}
