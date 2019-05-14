package com.foxminded.univer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.GroupDao;

@WebServlet("/groups")
public class GetGroups extends HttpServlet {

    private GroupDao groupDao = new GroupDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("groups", groupDao.findAll());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/showAllGroups.jsp").forward(req, resp);
    }
}
