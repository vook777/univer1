package com.foxminded.univer.servlet.lecture;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.LectureDao;

@WebServlet("/findLecture")
public class FindLecture extends HttpServlet {

    private LectureDao lectureDao = new LectureDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	Integer id = Integer.parseInt(req.getParameter("lectureId"));
    	try {
            req.setAttribute("lecture", lectureDao.findById(id).get());
        } catch (ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        req.getRequestDispatcher("/lecture/showLecture.jsp").forward(req, resp);
    }
}
