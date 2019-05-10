package com.foxminded.univer.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.foxminded.univer.dao.impl.StudentDao;
import com.foxminded.univer.models.Student;
// comment
public class GetStudentController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer studentId = Integer.parseInt(request.getParameter("studentId"));
        StudentDao studentDao = new StudentDao();
        Student studentToReturn = null;
        try {
            studentToReturn = studentDao.findById(studentId).get();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("student", studentToReturn);
        request.getRequestDispatcher("showStudent.jsp").forward(request, response);;
    }
}
