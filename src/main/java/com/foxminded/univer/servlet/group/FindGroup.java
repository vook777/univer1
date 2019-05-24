package com.foxminded.univer.servlet.group;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.GroupDao;

@WebServlet("/findGroup")
public class FindGroup extends HttpServlet {

    private GroupDao groupDao = new GroupDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	Integer id = Integer.parseInt(req.getParameter("groupId"));
    	try {
            req.setAttribute("group", groupDao.findById(id).get());
        } catch (ClassNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        req.getRequestDispatcher("/group/showGroup.jsp").forward(req, resp);
    }
}
