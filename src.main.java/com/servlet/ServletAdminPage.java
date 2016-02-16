package com.servlet;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.daoImpl.UserDaoImpl;
import com.java.User;

public class ServletAdminPage extends HttpServlet {

	private static final long serialVersionUID = -8529146298016378976L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		UserDaoImpl userImpl = new UserDaoImpl();
		Set<User> setUsers = userImpl.read();
		req.setAttribute("setUsers", setUsers);		

		req.getRequestDispatcher("adminPage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		UserDaoImpl userImpl = new UserDaoImpl();
		boolean deleted = userImpl.delete(id);	
		if (deleted) {
			Set<User> setUsers = userImpl.read();
			req.setAttribute("setUsers", setUsers);			
			req.getRequestDispatcher("adminPage.jsp").forward(req, resp);
		}
		
	}
	

}
