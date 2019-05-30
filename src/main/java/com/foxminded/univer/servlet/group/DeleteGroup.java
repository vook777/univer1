package com.foxminded.univer.servlet.group;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.GroupDao;
import com.foxminded.univer.models.Group;
import com.foxminded.univer.service.GroupService;

@WebServlet("/deleteGroup")
public class DeleteGroup extends HttpServlet {

	private GroupService groupService = new GroupService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Integer id = Integer.parseInt(req.getParameter("groupId"));
		try {
			Group groupToDelete = groupService.findById(id);
			groupService.delete(groupToDelete);
			req.setAttribute("deletedGroup", groupToDelete);
		} catch (ClassNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		req.getRequestDispatcher("/group/showDeleted.jsp").forward(req, resp);
	}
}