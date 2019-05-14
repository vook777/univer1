package com.foxminded.univer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.LectureDao;

@WebServlet("/lectures")
public class GetLectures extends HttpServlet {

    private LectureDao lectureDao = new LectureDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("lectures", lectureDao.findAll());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/showAllLectures.jsp").forward(req, resp);
    }
}
