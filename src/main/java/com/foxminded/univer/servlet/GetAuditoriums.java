package com.foxminded.univer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.AuditoriumDao;

@WebServlet("/auditoriums")
public class GetAuditoriums extends HttpServlet {

    private AuditoriumDao auditoriumDao = new AuditoriumDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("auditoriums", auditoriumDao.findAll());
        } catch (ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        getServletContext().getRequestDispatcher("/showAllAuditoriums.jsp").forward(req, resp);
    }
}
