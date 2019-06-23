package com.foxminded.univer.servlet.auditorium;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.AuditoriumDao;

@WebServlet("/findAuditorium")
public class FindAuditorium extends HttpServlet {

    private AuditoriumDao auditoriumDao = new AuditoriumDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	Integer id = Integer.parseInt(req.getParameter("auditoriumId"));
    	try {
            req.setAttribute("auditorium", auditoriumDao.findById(id).get());
        } catch (ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        req.getRequestDispatcher("/auditorium/showAuditorium.jsp").forward(req, resp);
    }
}
