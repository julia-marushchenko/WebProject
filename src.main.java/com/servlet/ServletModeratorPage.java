package com.servlet;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoImpl.UserDaoImpl;
import com.java.User;

public class ServletModeratorPage extends HttpServlet{

	private static final long serialVersionUID = -4539649765500982233L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		UserDaoImpl userImpl = new UserDaoImpl();
		Set<User> setUsers = userImpl.read();
		req.setAttribute("setUsers", setUsers);		
		
		req.getRequestDispatcher("moderatorPage.jsp").forward(req, resp);
	}
	

}
