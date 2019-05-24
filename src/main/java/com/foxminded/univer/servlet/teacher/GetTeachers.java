package com.foxminded.univer.servlet.teacher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.TeacherDao;

@WebServlet("/showAllTeachers")
public class GetTeachers extends HttpServlet {

    private TeacherDao teacherDao = new TeacherDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("teachers", teacherDao.findAll());
        } catch (ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        req.getRequestDispatcher("/teacher/showAllTeachers.jsp").forward(req, resp);
    }
}
