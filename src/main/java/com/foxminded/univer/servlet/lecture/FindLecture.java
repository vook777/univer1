package com.foxminded.univer.servlet.lecture;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.service.LectureService;

@WebServlet("/findLecture")
public class FindLecture extends HttpServlet {

    private LectureService lectureService = new LectureService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	Integer id = Integer.parseInt(req.getParameter("lectureId"));
    	try {
            req.setAttribute("lecture", lectureService.findById(id));
        } catch (ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        req.getRequestDispatcher("/lecture/showLecture.jsp").forward(req, resp);
    }
}
